package com.mehranj73.moviedb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mehranj73.moviedb.data.model.MovieEntity
import com.mehranj73.moviedb.data.model.TrendingEntity

@Dao
interface TrendingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trendingEntity: TrendingEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(trendingEntities: List<TrendingEntity>)

    @Query("SELECT * FROM trending_table")
    suspend fun getAllTrending(): List<TrendingEntity>
}