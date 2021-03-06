package com.lyj.itunesapp.di.module

import android.content.Context
import com.lyj.itunesapp.api.network.base.ApiBase
import com.lyj.itunesapp.api.network.base.NetworkConnectionInterceptor
import com.lyj.itunesapp.api.network.base.ServiceGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava3CallAdapterFactory.create()

    @Provides
    @Singleton
    fun providerConvertFactory(): Converter.Factory = GsonConverterFactory
        .create()

    @Provides
    @Singleton
    fun providerOkHttpClient(@ApplicationContext context : Context): OkHttpClient = OkHttpClient.Builder().let {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC
        it
            .addInterceptor(logger)
            .addInterceptor(NetworkConnectionInterceptor(context))
            .connectTimeout(20, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideServiceGenerator(): ServiceGenerator = ApiBase()
}