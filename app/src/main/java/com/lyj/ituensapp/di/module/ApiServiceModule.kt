package com.lyj.ituensapp.di.module

import com.lyj.ituensapp.api.network.base.ITunesService
import com.lyj.ituensapp.api.network.base.ServiceGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiServiceModule {

    @Provides
    @Singleton
    fun provideITunesService(
        serviceGenerator: ServiceGenerator,
        callAdapter: CallAdapter.Factory,
        converter: Converter.Factory,
        client: OkHttpClient
    ): ITunesService = serviceGenerator.generateService(
        ITunesService::class.java,
        client,
        callAdapter,
        converter
    )
}