package com.lyj.itunesapp.ui.main

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lyj.itunesapp.R
import com.lyj.itunesapp.api.database.entity.FavoriteTrackEntity
import com.lyj.itunesapp.api.network.api.ITunesService
import com.lyj.itunesapp.api.network.domain.ituenes.search.ResultsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    application: Application,
    private val iTuenesService: ITunesService,
) : AndroidViewModel(application) {
    val context: Context by lazy { getApplication<Application>().applicationContext }


    val favoriteDataSource : MutableLiveData<MutableList<FavoriteTrackEntity>> by lazy {
        MutableLiveData<MutableList<FavoriteTrackEntity>>(mutableListOf())
    }

    val trackListDataSource : MutableLiveData<MutableList<ResultsItem>> by lazy {
        MutableLiveData<MutableList<ResultsItem>>(mutableListOf())
    }

    fun requestITuenesData() = iTuenesService
        .searchITunesList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


}

enum class MainTabType(
    @StringRes val titleId : Int
){
    LIST(R.string.main_list_tab_title),
    FAVORITE(R.string.main_favorite_tab_title)
}

