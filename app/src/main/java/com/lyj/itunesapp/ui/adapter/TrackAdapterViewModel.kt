package com.lyj.itunesapp.ui.adapter

import android.content.Context
import com.lyj.itunesapp.core.base.AdapterViewModel
import com.lyj.itunesapp.ui.activites.main.MainTabType
import com.lyj.itunesapp.utils.SizeMeasurable
import io.reactivex.rxjava3.core.Observable

typealias TrackId = Int
typealias IsFavorite = Boolean

class TrackAdapterViewModel(
    override val context: Context,
    trackDataGettable: List<TrackDataGettable>,
    private val checkFavorite: CheckFavorite,
    val onFavoriteButtonClick : (Observable<Int>) -> Unit
) : AdapterViewModel<TrackData> {

    override var items : List<TrackData> = trackDataGettable.map { it.getTrackDataGettable(checkFavorite) }

    private var currentType : MainTabType? = null

    fun changeData(data : List<TrackDataGettable>, checkFavorite: CheckFavorite){
        items = data.map { it.getTrackDataGettable(checkFavorite) }
    }
}

data class TrackData(
    val trackId : Int?,
    val trackName : String?,
    val collectionName : String?,
    val artistName : String?,
    val url : String?,
    val isFavorite : IsFavorite?
)

@JvmInline
value class CheckFavorite(val invoke: (TrackId?) -> IsFavorite)

interface TrackDataGettable{
    fun getTrackDataGettable(checkFavorite: CheckFavorite) : TrackData
}