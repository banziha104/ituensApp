package com.lyj.itunesapp.utils

import android.content.Context
import android.util.DisplayMetrics


interface NeedContext {
    val context : Context
}

interface SizeMeasurable : NeedContext {
    val metrics: DisplayMetrics
        get() = context.resources.displayMetrics

    val dpHeight : Float
        get() = metrics.heightPixels / metrics.density

    val dpWidth : Float
        get() = metrics.widthPixels / metrics.density

    val pxWidth : Int
        get() = metrics.widthPixels

    val pxHeight : Int
        get() = metrics.heightPixels
}