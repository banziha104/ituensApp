package com.lyj.itunesapp.api.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lyj.itunesapp.api.network.domain.ituenes.search.ResultsItem
import com.lyj.itunesapp.ui.adapter.TrackData
import com.lyj.itunesapp.ui.adapter.TrackDataGettable

@Entity(tableName = "favorite_track")
data class FavoriteTrackEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    @ColumnInfo(name = "artwork_url_100")
    val artworkUrl100: String? = null,

    @ColumnInfo(name = "track_time_millis")
    val trackTimeMillis: Int? = null,

    @ColumnInfo(name = "country")
    val country: String? = null,

    @ColumnInfo(name = "preview_url")
    val previewUrl: String? = null,

    @ColumnInfo(name = "artist_id")
    val artistId: Int? = null,

    @ColumnInfo(name = "track_name")
    val trackName: String? = null,

    @ColumnInfo(name = "collection_name")
    val collectionName: String? = null,

    @ColumnInfo(name = "artist_view_url")
    val artistViewUrl: String? = null,

    @ColumnInfo(name = "disc_number")
    val discNumber: Int? = null,

    @ColumnInfo(name = "track_count")
    val trackCount: Int? = null,

    @ColumnInfo(name = "artwork_url_30")
    val artworkUrl30: String? = null,

    @ColumnInfo(name = "wrapper_type")
    val wrapperType: String? = null,

    @ColumnInfo(name = "currency")
    val currency: String? = null,

    @ColumnInfo(name = "collection_id")
    val collectionId: Int? = null,

    @ColumnInfo(name = "is_streamable")
    val isStreamable: Boolean? = null,

    @ColumnInfo(name = "track_explicitness")
    val trackExplicitness: String? = null,

    @ColumnInfo(name = "collection_view_url")
    val collectionViewUrl: String? = null,

    @ColumnInfo(name = "track_number")
    val trackNumber: Int? = null,

    @ColumnInfo(name = "release_date")
    val releaseDate: String? = null,

    @ColumnInfo(name = "kind")
    val kind: String? = null,

    @ColumnInfo(name = "trackId")
    val trackId: Int? = null,

    @ColumnInfo(name = "collection_price")
    val collectionPrice: Double? = null,

    @ColumnInfo(name = "disc_count")
    val discCount: Int? = null,

    @ColumnInfo(name = "primary_genre_name")
    val primaryGenreName: String? = null,

    @ColumnInfo(name = "track_price")
    val trackPrice: Double? = null,

    @ColumnInfo(name = "collection_explicitness")
    val collectionExplicitness: String? = null,

    @ColumnInfo(name = "track_view_url")
    val trackViewUrl: String? = null,

    @ColumnInfo(name = "artworkUrl60")
    val artworkUrl60: String? = null,

    @ColumnInfo(name = "track_censored_name")
    val trackCensoredName: String? = null,

    @ColumnInfo(name = "artist_mame")
    val artistName: String? = null,

    @ColumnInfo(name = "collection_censored_name")
    val collectionCensoredName: String? = null,

    @ColumnInfo(name = "content_advisory_rating")
    val contentAdvisoryRating: String? = null,

    @ColumnInfo(name = "collection_artist_name")
    val collectionArtistName: String? = null,

    @ColumnInfo(name = "collection_artist_id")
    val collectionArtistId: Int? = null,

    @ColumnInfo(name = "collection_artist_view_url")
    val collectionArtistViewUrl: String? = null
) : TrackDataGettable {

    companion object {

        // TODO : 코드량은 감소하지만, Acessible 문제, 성능 문제로 일단 보류
//        fun fromResponse(response: ResultsItem){
//            val favoriteTrackEntity  = FavoriteTrackEntity()
//            val responseFields = response::class.java.declaredFields
//
//            favoriteTrackEntity::class.java.declaredFields.forEach { favoriteField ->
//                val responseField = responseFields.firstOrNull { it.name == favoriteField.name }
//                if (responseField != null){
//                    favoriteField.isAccessible = true
//                    responseField.isAccessible = true
//                    favoriteField.set(favoriteTrackEntity,responseField.get(response))
//                }
//            }
//        }

        fun fromResponse(response : ResultsItem) : FavoriteTrackEntity = FavoriteTrackEntity(
            null,
            response.artworkUrl100,
            response.trackTimeMillis,
            response.country,
            response.previewUrl,
            response.artistId,
            response.trackName,
            response.collectionName,
            response.artistViewUrl,
            response.discNumber,
            response.trackCount,
            response.artworkUrl30,
            response.wrapperType,
            response.currency,
            response.collectionId,
            response.isStreamable,
            response.trackExplicitness,
            response.collectionViewUrl,
            response.trackNumber,
            response.releaseDate,
            response.kind,
            response.trackId,
            response.collectionPrice,
            response.discCount,
            response.primaryGenreName,
            response.trackPrice,
            response.collectionExplicitness,
            response.trackViewUrl,
            response.artworkUrl60,
            response.trackCensoredName,
            response.artistName,
            response.collectionCensoredName,
            response.contentAdvisoryRating,
            response.collectionArtistName,
            response.collectionArtistId,
            response.collectionArtistViewUrl,
        )
    }

    override fun getTrackDataGettable(): TrackData = TrackData(
        trackName,
        collectionName,
        artistName,
        artworkUrl60,
        null
    )
}