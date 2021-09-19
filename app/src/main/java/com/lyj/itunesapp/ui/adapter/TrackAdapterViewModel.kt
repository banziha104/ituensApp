package com.lyj.itunesapp.ui.adapter

import android.content.Context
import com.lyj.itunesapp.core.base.AdapterViewModel

class TrackAdapterViewModel(
    trackDataGettable: List<TrackDataGettable>,
    override val context: Context
) : AdapterViewModel<TrackData> {
    override val items : List<TrackData> = trackDataGettable.map { it.getTrackDataGettable() }

}

data class TrackData(
    val trackName : String?,
    val collectionName : String?,
    val artistName : String?,
    val url : String?,
    val isFavorite : Boolean?
)

@JvmInline
value class CheckFavorite(val checkFavorite: () -> Boolean)

interface TrackDataGettable{
    fun getTrackDataGettable() : TrackData
}