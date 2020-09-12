package com.mehranj73.moviedb.data.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import com.mehranj73.moviedb.data.model.Movie

interface MovieDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(movie: Movie): Long

    @Insert(onConflict = REPLACE)
    suspend fun insertList(movies: List<Movie>)

    @Delete
    suspend fun delete(movie: Movie)


}