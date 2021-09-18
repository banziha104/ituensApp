package com.lyj.ituensapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import com.lyj.ituensapp.R
import com.lyj.ituensapp.core.extension.android.selectedObserver
import com.lyj.ituensapp.core.extension.lang.simpleTag
import com.lyj.ituensapp.databinding.ActivityMainBinding
import com.trello.lifecycle4.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle4.LifecycleProvider
import com.trello.rxlifecycle4.android.RxLifecycleAndroid
import com.trello.rxlifecycle4.kotlin.bind
import com.trello.rxlifecycle4.kotlin.bindToLifecycle
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

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
            .subscribe { type ->
                supportFragmentManager.commit {
                    replace(
                        R.id.mainFrameLayout,
                        type.getFragment()
                    )
                }
            }
    }
}



