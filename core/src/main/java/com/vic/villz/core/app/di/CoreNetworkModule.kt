package com.vic.villz.core.app.di

import com.vic.villz.core.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
object CoreNetworkModule {

    @Provides
    @JvmStatic
    internal fun providesLoggingInterceptor(): HttpLoggingInterceptor? =
            if(BuildConfig.DEBUG){
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            } else null

    @Provides
    @JvmStatic
    internal fun providesOkhttpClientBuilder(loggingInterceptor: HttpLoggingInterceptor?): OkHttpClient.Builder =
            OkHttpClient.Builder()
                .apply {
                    loggingInterceptor?.also {
                        addInterceptor(it)
                    }
                }

    //add retrofit
}