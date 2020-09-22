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


    @Singleton
    @Provides
    fun provideMovieRepository(
         retrofitService: RetrofitService,
         movieDao: MovieDao
    ): MovieRepository{
        return MovieRepositoryImpl(retrofitService, movieDao)
    }

    @Singleton
    @Provides
    fun provideTrendingRepository(
         retrofitService: RetrofitService,
         trendingDao: TrendingDao
    ): TrendingRepository{
        return TrendingRepositoryImpl(retrofitService, trendingDao)
    }

}