package com.lyj.itunesapp.di.module

import android.content.Context
import androidx.room.Room
import com.lyj.itunesapp.api.database.ITunesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): ITunesDataBase =
        Room.databaseBuilder(
            context, ITunesDataBase::class.java,"itunes.db"
        ).build()
}