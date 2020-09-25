package com.mehranj73.moviedb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mehranj73.moviedb.data.model.MovieEntity
import com.mehranj73.moviedb.data.model.TrendingEntity
import com.mehranj73.moviedb.data.model.TvEntity


@Database(entities = [MovieEntity::class, TrendingEntity::class, TvEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun trendingDao(): TrendingDao

    abstract fun tvDao(): TvDao

    companion object{
        const val DATABASE_NAME: String = "App_db"
    }
}