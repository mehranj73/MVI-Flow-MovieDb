package com.mehranj73.moviedb.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mehranj73.moviedb.data.remote.RetrofitService
import com.mehranj73.moviedb.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.FlowPreview
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@FlowPreview
@InstallIn(ApplicationComponent::class)
@Module
object RetrofitModule {


    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, baseUrl: String): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit.Builder): RetrofitService {
        return retrofit
            .build()
            .create(RetrofitService::class.java)
    }

}