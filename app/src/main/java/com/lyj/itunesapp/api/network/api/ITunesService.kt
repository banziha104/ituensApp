package com.lyj.itunesapp.api.network.api

import com.lyj.itunesapp.api.network.domain.ituenes.search.ITunesSearchResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesService {
    @GET("search/")
    fun searchITunesList(
        @Query("term") term : String = "greenday",
        @Query("entity") entity : String = "song",
        @Query("limit") limit : Int = 30,
        @Query("offset") offset : Int = 0,
    ) : Single<ITunesSearchResponse>
}