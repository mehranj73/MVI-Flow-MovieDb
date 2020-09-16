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

    @Query("SELECT * FROM movie_table WHERE id= :movieId")
    suspend fun getMovie(movieId: Int): Movie

    @Query(
        """
        UPDATE movie_table SET budget = :budget, revenue = :revenue,
runtime = :runtime, status =:status WHERE id = :id
    """
    )
    suspend fun updateMovie(id: Int, budget: Int, revenue: Int, runtime: Int, status: String)

}