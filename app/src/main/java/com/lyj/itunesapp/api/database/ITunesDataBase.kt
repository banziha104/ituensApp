package com.lyj.itunesapp.api.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lyj.itunesapp.api.database.dao.FavoriteDao
import com.lyj.itunesapp.api.database.entity.FavoriteTrackEntity

@Database(
    entities = [FavoriteTrackEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ITunesDataBase : RoomDatabase(){
    abstract fun favoriteDao() : FavoriteDao
}