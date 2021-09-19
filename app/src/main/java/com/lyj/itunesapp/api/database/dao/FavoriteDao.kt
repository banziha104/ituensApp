package com.lyj.itunesapp.api.database.dao

import androidx.room.*
import com.lyj.itunesapp.api.database.entity.FavoriteTrackEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_track")
    fun findAllOnce() : Single<List<FavoriteTrackEntity>>

    @Query("SELECT * FROM favorite_track")
    fun findAllContinuous() : Flowable<List<FavoriteTrackEntity>>

    @Query("SELECT * FROM favorite_track WHERE track_id == :trackId")
    fun findByTrackId(trackId : Long) : Single<List<FavoriteTrackEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: FavoriteTrackEntity) : Completable

    @Query("DELETE FROM favorite_track WHERE id == :id")
    fun delete(id : Long) : Completable
}