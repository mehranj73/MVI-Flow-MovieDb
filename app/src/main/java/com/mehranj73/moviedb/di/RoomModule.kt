package com.mehranj73.moviedb.di

import android.content.Context
import androidx.room.Room
import com.mehranj73.moviedb.data.local.AppDatabase
import com.mehranj73.moviedb.data.local.MovieDao
import com.mehranj73.moviedb.data.local.TrendingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@FlowPreview
@InstallIn(ApplicationComponent::class)
@Module
object RoomModule {


    @Singleton
    @Provides
    fun provideBlogDb(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideTrendingDao(appDatabase: AppDatabase): TrendingDao {
        return appDatabase.trendingDao()
    }

}