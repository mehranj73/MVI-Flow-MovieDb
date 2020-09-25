package com.mehranj73.moviedb.data.local

import androidx.room.*
import com.mehranj73.moviedb.data.model.TvEntity

@Dao
interface TvDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tvEntity: TvEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(tvs: List<TvEntity>)

    @Delete
    suspend fun delete(tvEntity: TvEntity)

    @Query("SELECT * FROM tv_table")
    suspend fun getTvs(): List<TvEntity>

    @Query("SELECT * FROM tv_table WHERE id= :tvId")
    suspend fun getTv(tvId: Int): TvEntity

}