package com.mehranj73.moviedb.di

import com.mehranj73.moviedb.data.local.MovieDao
import com.mehranj73.moviedb.data.local.TrendingDao
import com.mehranj73.moviedb.data.local.TvDao
import com.mehranj73.moviedb.data.remote.RetrofitService
import com.mehranj73.moviedb.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@FlowPreview
@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        retrofitService: RetrofitService,
        movieDao: MovieDao
    ): MovieRepository {
        return MovieRepositoryImpl(retrofitService, movieDao)
    }

    @Singleton
    @Provides
    fun provideTrendingRepository(
        retrofitService: RetrofitService,
        trendingDao: TrendingDao
    ): TrendingRepository {
        return TrendingRepositoryImpl(retrofitService, trendingDao)
    }
    @Singleton
    @Provides
    fun provideTvRepository(
        retrofitService: RetrofitService,
        tvDao: TvDao
    ): TvRepository {
        return TvRepositoryImpl(retrofitService, tvDao)
    }

}