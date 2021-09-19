package com.lyj.itunesapp.ui.activites.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.recyclerview.scrollEvents
import com.lyj.itunesapp.R
import com.lyj.itunesapp.api.database.entity.FavoriteTrackEntity
import com.lyj.itunesapp.api.network.api.ITunesService
import com.lyj.itunesapp.api.network.domain.ituenes.search.ResultsItem
import com.lyj.itunesapp.core.extension.android.longToast
import com.lyj.itunesapp.core.extension.android.selectedObserver
import com.lyj.itunesapp.core.extension.android.unwrappedValue
import com.lyj.itunesapp.core.extension.lang.SchedulerType
import com.lyj.itunesapp.core.extension.lang.applyScheduler
import com.lyj.itunesapp.databinding.ActivityMainBinding
import com.lyj.itunesapp.exceptions.livedata.LiveDataNotInitializedException
import com.lyj.itunesapp.exceptions.network.SearchResultsIsEmptyException
import com.lyj.itunesapp.ui.adapter.TrackAdapter
import com.lyj.itunesapp.ui.adapter.TrackAdapterViewModel
import com.trello.lifecycle4.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle4.LifecycleProvider
import com.trello.rxlifecycle4.kotlin.bindToLifecycle
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
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
            viewModel.trackCheckFavorite(viewModel.favoriteListLiveData.unwrappedValue)
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

    private var currentTabType: MainTabType = MainTabType.LIST

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
            }
    }


    private fun observeRxSource() {
        observeInitializeData()
        observeBottomNavigation()
        observeDataChange()
        observeScrollEvent()
    }

    private fun observeScrollEvent() {
        binding
            .mainRecyclerView
            .scrollEvents()
            .bindToLifecycle(provider)
            .filter { !it.view.canScrollVertically(1) && currentTabType == MainTabType.LIST }
            .throttleFirst(1, TimeUnit.SECONDS)
            .applyScheduler(subscribeOn = SchedulerType.MAIN, observeOn = SchedulerType.IO)
            .switchMapSingle {
                viewModel.requestITunesData(viewModel.offsetLiveData.unwrappedValue + ITunesService.LIMIT)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { showProgress() }
            .subscribe({
                val results = it.results?.filterNotNull()
                if (results != null && results.isNotEmpty() && it.resultCount != 0) {
                    val trackList = viewModel.trackListLiveData.unwrappedValue
                    trackList.addAll(results)
                    trackList.distinct()
                    viewModel.trackListLiveData.value = trackList
                    viewModel.offsetLiveData.postValue(viewModel.offsetLiveData.unwrappedValue + ITunesService.LIMIT)
                }else{
                    longToast(R.string.main_empty_api_results)
                }
                hideProgress()
            }, {
                if (it is LiveDataNotInitializedException){
                    longToast(it.msg)
                }
                hideProgress()
                it.printStackTrace()
            })
    }

    private fun observeInitializeData() {
        viewModel
            .initializeData(this)
            .bindToLifecycle(provider)
            .subscribe({ (trackResponse, favoriteList) ->
                val results = trackResponse.results
                if (results != null && results.isNotEmpty()) {
                    viewModel.trackListLiveData.postValue(results.filterNotNull().toMutableList())
                } else {
                    throw SearchResultsIsEmptyException()
                }
                viewModel.favoriteListLiveData.postValue(favoriteList)
            }, {
                if(it is SearchResultsIsEmptyException){
                    longToast(it.msg)
                }
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
                when (type) {
                    MainTabType.LIST -> {
                        binding
                            .mainRecyclerView
                            .apply {
                                adapterViewModel.changeData(
                                    trackList,
                                    viewModel.trackCheckFavorite(favorites)
                                )
                                // TODO : 최적화로 동작은 하나, Favorite 클릭시 버튼이 갱신이 안되어서 전체 notify
                                trackAdapter.notifyDataSetChanged()
//                                trackAdapter.notifyItemRangeChanged(viewModel.offsetLiveData.value!!,ITunesService.LIMIT)
                            }
                    }
                    MainTabType.FAVORITE -> {
                        if (favorites.isEmpty()) {
                            longToast(R.string.main_empty_favorite)
                        }
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

    override fun hideProgress() {
        binding.mainProgressBar.visibility = View.GONE
    }

}

interface MainProgressController {
    fun showProgress()
    fun hideProgress()
}


