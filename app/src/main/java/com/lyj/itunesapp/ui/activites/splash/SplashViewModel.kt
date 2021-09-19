package com.lyj.itunesapp.ui.activites.splash

import android.Manifest
import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import com.lyj.itunesapp.core.permission.DialogCallBack
import com.lyj.itunesapp.core.permission.IsAllGranted
import com.lyj.itunesapp.core.permission.PermissionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val permissionManager: PermissionManager
) : ViewModel() {

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.INTERNET,
    )

    fun checkAndRequestPermission(activity: Activity): Single<IsAllGranted> = permissionManager.checkAndRequestPermission(activity, permissions)


    fun buildPermissionAlertDialog(
        context: Context,
        positiveEvent: DialogCallBack,
        negetiveEvent: DialogCallBack
    ) = permissionManager.buildAlertDialog(context, positiveEvent, negetiveEvent)

}