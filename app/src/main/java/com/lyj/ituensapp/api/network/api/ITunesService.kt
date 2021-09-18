package com.lyj.ituensapp.api.network.api

import com.lyj.ituensapp.api.network.domain.ituenes.search.ITuensSearchResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ITunesService {
    @GET("search/")
    fun searchITunesList(
        @Query("term") term : String = "greenday",
        @Query("entity") entity : String = "song",
        @Query("limit") limit : Int = 10,
        @Query("offset") offset : Int = 0,
    ) : Single<ITuensSearchResponse>
}