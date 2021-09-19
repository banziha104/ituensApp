package com.lyj.itunesapp.di.module

import android.content.Context
import com.lyj.itunesapp.core.permission.PermissionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class PermissionModule {

    @Provides
    @Singleton
    fun providePermissionManager(
        @ApplicationContext context : Context
    ) : PermissionManager = PermissionManager(context)
}