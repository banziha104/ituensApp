package com.lyj.itunesapp.exceptions.network

import java.lang.RuntimeException

class SearchResultsIsEmptyException(val msg : String = "데이터를 받아왔으나, 결과를 표시할 데이터가 없습니다") : RuntimeException(msg)