package com.lyj.itunesapp.ui.activites.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.recyclerview.scrollEvents
import com.lyj.itunesapp.R
import com.lyj.itunesapp.api.database.entity.FavoriteTrackEntity
import com.lyj.itunesapp.api.network.api.ITunesService
import com.lyj.itunesapp.api.network.domain.ituenes.search.ResultsItem
import com.lyj.itunesapp.core.extension.android.longToast
import com.lyj.itunesapp.core.extension.android.selectedObserver
import com.lyj.itunesapp.core.extension.lang.SchedulerType
import com.lyj.itunesapp.core.extension.lang.applyScheduler
import com.lyj.itunesapp.core.extension.lang.mapTag
import com.lyj.itunesapp.databinding.ActivityMainBinding
import com.lyj.itunesapp.ui.adapter.CheckFavorite
import com.lyj.itunesapp.ui.adapter.TrackAdapter
import com.lyj.itunesapp.ui.adapter.TrackAdapterViewModel
import com.lyj.itunesapp.ui.adapter.TrackDataGettable
import com.trello.lifecycle4.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle4.LifecycleProvider
import com.trello.rxlifecycle4.kotlin.bindToLifecycle
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.Observables
import okhttp3.internal.notifyAll
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainProgressController {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainActivityViewModel by viewModels()
    private val provider: LifecycleProvider<Lifecycle.Event> =
        AndroidLifecycle.createLifecycleProvider(this)
    private val adapterViewModel: TrackAdapterViewModel by lazy {
        TrackAdapterViewModel(
            this,
            listOf(),
            viewModel.trackCheckFavorite(viewModel.favoriteListLiveData.value ?: listOf())
        ) { observable ->
            observable
                .bindToLifecycle(provider)
                .applyScheduler(subscribeOn = SchedulerType.MAIN, observeOn = SchedulerType.IO)
                .flatMapCompletable { trackId ->
                    viewModel.favoriteInsertOrDelete(trackId)
                }.subscribe({}, {
                    it.printStackTrace()
                })
        }
    }

    private val trackAdapter: TrackAdapter by lazy {
        TrackAdapter(adapterViewModel)
    }

    private var currentTabType : MainTabType = MainTabType.LIST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()
        observeRxSource()
    }

    private fun initRecyclerView() {
        binding
            .mainRecyclerView
            .apply {
                adapter = trackAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
//                scrollEvents()
//                    .bindToLifecycle(provider)
//                    .filter { !it.view.canScrollVertically(1) && currentTabType == MainTabType.LIST }
//                    .throttleFirst(1, TimeUnit.SECONDS)
//                    .switchMapSingle {
//                        viewModel.requestITunesData(ITunesService.currentOffset + ITunesService.LIMIT)
//                    }
//                    .subscribe {
//
//                    }
            }
    }


    private fun observeRxSource() {
        observeInitializeData()
        observeBottomNavigation()
        observeDataChange()
    }

    private fun observeInitializeData() {
        viewModel
            .initializeData(provider, this)
            .bindToLifecycle(provider)
            .subscribe({ (trackResponse, favoriteList) ->
                val results = trackResponse.results
                if (results != null && results.isNotEmpty()) {
                    viewModel.trackListLiveData.postValue(results.filterNotNull().toMutableList())
                } else {

                }
                viewModel.favoriteListLiveData.postValue(favoriteList)
            }, {
                it.printStackTrace()
            })
    }

    private fun observeDataChange() {
        viewModel.observeFavoriteChanges()
            .bindToLifecycle(provider)
            .subscribe({
                viewModel.favoriteListLiveData.postValue(it)
            }, {
                it.printStackTrace()
            })
    }

    private fun observeBottomNavigation() {
        Observable.combineLatest(
            binding.mainBottomNavigationView.selectedObserver(
                this,
                currentTabType
            ),
            Observable.create<List<FavoriteTrackEntity>> { emitter ->
                viewModel.favoriteListLiveData.observe(this) { emitter.onNext(it) }
            },
            Observable.create<MutableList<ResultsItem>> { emitter ->
                viewModel.trackListLiveData.observe(this) { emitter.onNext(it) }
            },
            { type, favorite, trackList -> Triple(type, favorite, trackList) })
            .applyScheduler(subscribeOn = SchedulerType.MAIN, observeOn = SchedulerType.MAIN)
            .subscribe({ (type, favorites, trackList) ->

                currentTabType = type

                when (type) {
                    MainTabType.LIST -> {
                        binding
                            .mainRecyclerView
                            .apply {
                                adapterViewModel.changeData(
                                    trackList,
                                    viewModel.trackCheckFavorite(favorites)
                                )
                                trackAdapter.notifyDataSetChanged()
                            }
                    }
                    MainTabType.FAVORITE -> {
                        binding
                            .mainRecyclerView
                            .apply {
                                adapterViewModel.changeData(
                                    favorites,
                                    viewModel.favoriteCheckFavorite()
                                )
                                trackAdapter.notifyDataSetChanged()
                            }
                    }
                }
            }, {
                it.printStackTrace()
            })
    }

    override fun showProgress() {
        binding.mainProgressBar.visibility = View.VISIBLE
    }

    override fun stopProgress() {
        binding.mainProgressBar.visibility = View.GONE
    }

}

interface MainProgressController {
    fun showProgress()
    fun stopProgress()
}


