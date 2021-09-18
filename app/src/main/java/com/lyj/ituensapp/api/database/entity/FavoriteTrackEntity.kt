package com.lyj.ituensapp.api.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorite_track")
data class FavoriteTrackEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long,
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

    @ColumnInfo(name = "trackCensoredName")
    val trackCensoredName: String? = null,

    @field:SerializedName("artistName")
    val artistName: String? = null,

    @field:SerializedName("collectionCensoredName")
    val collectionCensoredName: String? = null,

    @field:SerializedName("contentAdvisoryRating")
    val contentAdvisoryRating: String? = null,

    @field:SerializedName("collectionArtistName")
    val collectionArtistName: String? = null,

    @field:SerializedName("collectionArtistId")
    val collectionArtistId: Int? = null,

    @field:SerializedName("collectionArtistViewUrl")
    val collectionArtistViewUrl: String? = null
)