package com.lyj.itunesapp.ui.activites.main

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.lyj.itunesapp.R
import com.lyj.itunesapp.api.database.ITunesDataBase
import com.lyj.itunesapp.api.database.dao.FavoriteDao
import com.lyj.itunesapp.api.database.entity.FavoriteTrackEntity
import com.lyj.itunesapp.api.network.api.ITunesService
import com.lyj.itunesapp.api.network.domain.ituenes.search.ITunesSearchResponse
import com.lyj.itunesapp.api.network.domain.ituenes.search.ResultsItem
import com.lyj.itunesapp.core.extension.lang.SchedulerType
import com.lyj.itunesapp.core.extension.lang.applyScheduler
import com.lyj.itunesapp.ui.adapter.CheckFavorite
import com.trello.rxlifecycle4.LifecycleProvider
import com.trello.rxlifecycle4.kotlin.bindToLifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.NullPointerException
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    application: Application,
    database: ITunesDataBase,
    private val iTuenesService: ITunesService,
) : AndroidViewModel(application) {
    val context: Context by lazy { getApplication<Application>().applicationContext }

    private val favoriteDao = database.favoriteDao()

    val favoriteListLiveData: MutableLiveData<List<FavoriteTrackEntity>> by lazy {
        MutableLiveData<List<FavoriteTrackEntity>>(listOf())
    }

    val trackListLiveData: MutableLiveData<MutableList<ResultsItem>> by lazy {
        MutableLiveData<MutableList<ResultsItem>>(mutableListOf())
    }

    fun favoriteInsertOrDelete(trackId: Int): Completable =
        favoriteDao
            .findByTrackId(trackId.toLong())
            .flatMapCompletable { list ->
                if (list.isNotEmpty() && list[0].id != null) {
                    favoriteDao.delete(list[0].id!!)
                } else {
                    val data = trackListLiveData.value?.first { it.trackId == trackId }
                        ?: throw ClassNotFoundException()
                    favoriteDao.insert(FavoriteTrackEntity.fromResponse(data))
                }
            }
            .applyScheduler(subscribeOn = SchedulerType.IO,observeOn = SchedulerType.IO)

    fun requestITunesData(offset: Int): Single<ITunesSearchResponse> = iTuenesService
        .searchITunesList(offset = offset)
        .applyScheduler(subscribeOn = SchedulerType.IO, observeOn = SchedulerType.IO)

    private fun findAllFavoriteOnce(): Single<List<FavoriteTrackEntity>> =
        favoriteDao
            .findAllOnce()
            .applyScheduler(subscribeOn = SchedulerType.IO, observeOn = SchedulerType.IO)

    fun observeFavoriteChanges(): Flowable<List<FavoriteTrackEntity>> =
        favoriteDao.findAllContinuous()
            .onBackpressureBuffer()
            .applyScheduler(subscribeOn = SchedulerType.IO, observeOn = SchedulerType.IO)


    fun initializeData(
        provider: LifecycleProvider<Lifecycle.Event>,
        progressController: MainProgressController
    ): Single<Pair<ITunesSearchResponse, List<FavoriteTrackEntity>>> =
        Single.zip(
            requestITunesData(ITunesService.currentOffset)
                .bindToLifecycle(provider),
            findAllFavoriteOnce()
                .bindToLifecycle(provider),
        ) { trackResponse, favoriteList ->
            trackResponse to favoriteList
        }
            .applyScheduler(SchedulerType.IO, SchedulerType.MAIN)
            .doOnSubscribe { progressController.showProgress() }
            .doOnSuccess { progressController.stopProgress() }
            .doOnError { progressController.stopProgress() }
            .observeOn(Schedulers.io())

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