package com.mehranj73.moviedb.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.mehranj73.moviedb.data.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(movie: Movie): Long

    @Insert(onConflict = REPLACE)
    suspend fun insertList(movies: List<Movie>)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * FROM movie_table")
    suspend fun getMovies(): List<Movie>


}