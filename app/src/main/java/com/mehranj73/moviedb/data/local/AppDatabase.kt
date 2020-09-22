package com.mehranj73.moviedb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mehranj73.moviedb.data.model.MovieEntity
import com.mehranj73.moviedb.data.model.TrendingEntity


@Database(entities = [MovieEntity::class, TrendingEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun trendingDao(): TrendingDao

    companion object{
        const val DATABASE_NAME: String = "App_db"
    }
}