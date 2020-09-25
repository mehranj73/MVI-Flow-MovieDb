package com.mehranj73.moviedb.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mehranj73.moviedb.R
import com.mehranj73.moviedb.data.local.AppDatabase
import com.mehranj73.moviedb.data.local.MovieDao
import com.mehranj73.moviedb.data.local.TrendingDao
import com.mehranj73.moviedb.data.remote.RetrofitService
import com.mehranj73.moviedb.data.repository.MovieRepository
import com.mehranj73.moviedb.data.repository.MovieRepositoryImpl
import com.mehranj73.moviedb.data.repository.TrendingRepository
import com.mehranj73.moviedb.data.repository.TrendingRepositoryImpl
import com.mehranj73.moviedb.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.FlowPreview
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@FlowPreview
@InstallIn(ApplicationComponent::class)
@Module
object AppModule {


    @Singleton
    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.drawable.default_image)
            .error(R.drawable.default_image)
    }

    @Singleton
    @Provides
    fun provideGlideInstance(application: Application, requestOptions: RequestOptions): RequestManager {
        return Glide.with(application)
            .setDefaultRequestOptions(requestOptions)
    }




}