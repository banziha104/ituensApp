package com.lyj.itunesapp.core.extension.android

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes


fun Context.longToast(
    @StringRes res : Int
) = Toast.makeText(this,res, Toast.LENGTH_LONG)


fun Context.longToast(
    str: String
) = Toast.makeText(this,str,Toast.LENGTH_LONG)


fun Context.shortToast(
    @StringRes res : Int
) = Toast.makeText(this,res,Toast.LENGTH_SHORT)


fun Context.shortToast(
    str : String
) = Toast.makeText(this,str,Toast.LENGTH_SHORT)