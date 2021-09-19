package com.lyj.itunesapp.api.database.dao

import androidx.room.*
import com.lyj.itunesapp.api.database.entity.FavoriteTrackEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_track")
    fun findToken() : Single<List<FavoriteTrackEntity>>

    @Query("SELECT * FROM favorite_track")
    fun observeToken() : Flowable<List<FavoriteTrackEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: FavoriteTrackEntity) : Completable

    @Delete
    fun delete(id : Long) : Completable
}