package com.lyj.ituensapp.ui.main

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lyj.ituensapp.R
import com.lyj.ituensapp.api.network.api.ITunesService
import com.lyj.ituensapp.ui.main.fragments.favorites.FavoritesFragment
import com.lyj.ituensapp.ui.main.fragments.list.ListFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    application: Application,
    iTuenesService: ITunesService,
) : AndroidViewModel(application) {
    val context: Context by lazy { getApplication<Application>().applicationContext }

    val requestITuenesData = iTuenesService
        .searchITunesList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    val favoriteDataSource : MutableLiveData<List>
}

enum class MainTabType(
    @StringRes val titleId : Int
){
    LIST(R.string.main_list_tab_title){
        override fun getFragment() : Fragment = ListFragment.getInstance()
    },
    FAVORITE(R.string.main_favorite_tab_title){
        override fun getFragment() : Fragment = FavoritesFragment.getInstance()
    };
    abstract fun getFragment() : Fragment
}

