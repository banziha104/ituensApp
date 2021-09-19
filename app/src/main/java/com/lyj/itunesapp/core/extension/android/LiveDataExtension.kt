package com.lyj.itunesapp.core.extension.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lyj.itunesapp.exceptions.livedata.LiveDataNotInitializedException


val <T> MutableLiveData<T>.unwrappedValue
       get() = this.value ?: throw LiveDataNotInitializedException()