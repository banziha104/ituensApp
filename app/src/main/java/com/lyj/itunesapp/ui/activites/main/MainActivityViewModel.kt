package com.lyj.itunesapp.ui.activites.main

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lyj.itunesapp.R
import com.lyj.itunesapp.api.database.ITunesDataBase
import com.lyj.itunesapp.api.database.entity.FavoriteTrackEntity
import com.lyj.itunesapp.api.network.api.ITunesService
import com.lyj.itunesapp.api.network.domain.ituenes.search.ITunesSearchResponse
import com.lyj.itunesapp.api.network.domain.ituenes.search.ResultsItem
import com.lyj.itunesapp.core.extension.android.unwrappedValue
import com.lyj.itunesapp.core.extension.lang.SchedulerType
import com.lyj.itunesapp.core.extension.lang.applyScheduler
import com.lyj.itunesapp.exceptions.validation.NotMacthedTrackByTrackIdExeption
import com.lyj.itunesapp.ui.adapter.CheckFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

typealias OffSet = Int

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    application: Application,
    database: ITunesDataBase,
    private val iTunesService: ITunesService,
) : AndroidViewModel(application) {
    val context: Context by lazy { getApplication<Application>().applicationContext }

    private val favoriteDao = database.favoriteDao()

    val favoriteListLiveData: MutableLiveData<List<FavoriteTrackEntity>> by lazy {
        MutableLiveData<List<FavoriteTrackEntity>>(listOf())
    }

    val trackListLiveData: MutableLiveData<MutableList<ResultsItem>> by lazy {
        MutableLiveData<MutableList<ResultsItem>>(mutableListOf())
    }

    val offsetLiveData : MutableLiveData<OffSet> by lazy {
        MutableLiveData<OffSet>(ITunesService.currentOffset)
    }

    fun favoriteInsertOrDelete(trackId: Int): Completable =
        favoriteDao
            .findByTrackId(trackId.toLong())
            .flatMapCompletable { list ->
                if (list.isNotEmpty() && list[0].id != null) {
                    favoriteDao.delete(list[0].id!!)
                } else {
                    val data = trackListLiveData.unwrappedValue.firstOrNull { it.trackId == trackId } ?: throw NotMacthedTrackByTrackIdExeption()
                    favoriteDao.insert(FavoriteTrackEntity.fromResponse(data))
                }
            }
            .retry(2)
            .applyScheduler(subscribeOn = SchedulerType.IO,observeOn = SchedulerType.IO)

    fun requestITunesData(offset: Int): Single<ITunesSearchResponse> = iTunesService
        .searchITunesList(offset = offset)
        .applyScheduler(subscribeOn = SchedulerType.IO, observeOn = SchedulerType.IO)
        .retry(2)

    private fun findAllFavoriteOnce(): Single<List<FavoriteTrackEntity>> =
        favoriteDao
            .findAllOnce()
            .applyScheduler(subscribeOn = SchedulerType.IO, observeOn = SchedulerType.IO)
            .retry(2)

    fun observeFavoriteChanges(): Flowable<List<FavoriteTrackEntity>> =
        favoriteDao.findAllContinuous()
            .onBackpressureBuffer()
            .applyScheduler(subscribeOn = SchedulerType.IO, observeOn = SchedulerType.IO)



    fun initializeData(
        progressController: MainProgressController
    ): Single<Pair<ITunesSearchResponse, List<FavoriteTrackEntity>>> =
        Single.zip(
            requestITunesData(ITunesService.currentOffset),
            findAllFavoriteOnce()
        ) { trackResponse, favoriteList ->
            trackResponse to favoriteList
        }
            .applyScheduler(SchedulerType.IO, SchedulerType.MAIN)
            .doOnSubscribe { progressController.showProgress() }
            .doOnSuccess { progressController.hideProgress() }
            .doOnError { progressController.hideProgress() }
            .observeOn(Schedulers.io())
            .retry(2)

    fun trackCheckFavorite(favorites : List<FavoriteTrackEntity>) : CheckFavorite = CheckFavorite{ trackId ->
        if (trackId == null){
            false
        }else{
            trackId in favorites.map { it.trackId }
        }
    }

    fun favoriteCheckFavorite() : CheckFavorite = CheckFavorite {
        true
    }
}

enum class MainTabType(
    @StringRes val titleId: Int
) {
    LIST(R.string.main_list_tab_title),
    FAVORITE(R.string.main_favorite_tab_title)
}
