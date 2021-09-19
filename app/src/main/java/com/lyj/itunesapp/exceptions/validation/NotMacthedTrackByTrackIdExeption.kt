package com.lyj.itunesapp.exceptions.validation

import java.lang.RuntimeException

class NotMacthedTrackByTrackIdExeption(val msg : String = "trackId 와 일치하는 Track 정보가 없습니다") : RuntimeException(msg)