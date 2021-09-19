package com.lyj.itunesapp.exceptions.livedata

import java.lang.RuntimeException

class LiveDataNotInitializedException(val msg : String = "LiveData가 초기화 되지 않았습니다") : RuntimeException(msg)