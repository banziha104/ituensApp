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
        @Query("limit") limit : Int = LIMIT,
        @Query("offset") offset : Int = currentOffset,
    ) : Single<ITunesSearchResponse>

    companion object{
        const val LIMIT = 30
        var currentOffset = 0
    }
}