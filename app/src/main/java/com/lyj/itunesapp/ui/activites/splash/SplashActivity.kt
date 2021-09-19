package com.lyj.itunesapp.ui.activites.splash

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import com.lyj.itunesapp.R
import com.lyj.itunesapp.core.extension.lang.permissionTag
import com.lyj.itunesapp.core.permission.IsAllGranted
import com.lyj.itunesapp.ui.activites.main.MainActivity
import com.trello.lifecycle4.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle4.LifecycleProvider
import com.trello.rxlifecycle4.kotlin.bindToLifecycle
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Single

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel : SplashViewModel by viewModels()
    private val provider : LifecycleProvider<Lifecycle.Event> = AndroidLifecycle.createLifecycleProvider(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        observePermission(viewModel.checkAndRequestPermission(this))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val denied = grantResults.filter { it == PackageManager.PERMISSION_DENIED }
        if (denied.isNotEmpty()) {
            viewModel.buildPermissionAlertDialog(
                this,
                positiveEvent = { dialog, _ ->
                    observePermission(viewModel.checkAndRequestPermission(this))
                    dialog.dismiss()
                },
                negetiveEvent = { dialog, _ ->
                    dialog.dismiss()
                    finishAffinity()
                }
            ).show()
        }else{
            moveToMainActivity()
        }
    }


    private fun observePermission(
        single: Single<IsAllGranted>
    ) = single
        .bindToLifecycle(provider)
        .subscribe({ isAllGranted ->
            Log.d(permissionTag, "isAllGranted :$isAllGranted")
            if (isAllGranted) {
                moveToMainActivity()
            }
        }, {
            it.printStackTrace()
        })


    private fun moveToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}