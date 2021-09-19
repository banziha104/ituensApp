package com.lyj.itunesapp.core.base

import android.content.Context

interface AdapterViewModel<T> {
    val items : Collection<T>
    val context : Context

    val itemCount : Int
    get() = items.size
}