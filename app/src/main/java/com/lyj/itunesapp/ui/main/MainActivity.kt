package com.lyj.itunesapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import com.lyj.ituensapp.databinding.ActivityMainBinding
import com.lyj.itunesapp.core.extension.android.selectedObserver
import com.trello.lifecycle4.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle4.LifecycleProvider
import com.trello.rxlifecycle4.kotlin.bindToLifecycle
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel : MainActivityViewModel by viewModels()
    private val provider : LifecycleProvider<Lifecycle.Event> = AndroidLifecycle.createLifecycleProvider(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeBottomNavigation()
    }

    private fun observeBottomNavigation(){
        binding
            .mainBottomNavigationView
            .selectedObserver(this,MainTabType.LIST)
            .bindToLifecycle(provider)
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { type ->
                when(type){
                    MainTabType.LIST -> {

                    }
                    MainTabType.FAVORITE -> {

                    }
                }
                Observable.just(1)
            }
    }
}



