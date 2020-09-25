package com.mehranj73.moviedb.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.mehranj73.moviedb.data.model.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(movieEntity: MovieEntity): Long

    @Insert(onConflict = REPLACE)
    suspend fun insertList(movies: List<MovieEntity>)

    @Delete
    suspend fun delete(movieEntity: MovieEntity)

    @Query("SELECT * FROM movie_table")
    suspend fun getMovies(): List<MovieEntity>

    @Query("SELECT * FROM movie_table WHERE id= :movieId")
    suspend fun getMovie(movieId: Int): MovieEntity

    @Query(
        """
        UPDATE movie_table SET budget = :budget, revenue = :revenue,
                    runtime = :runtime, status =:status WHERE id = :id
    """
    )
    suspend fun updateMovie(id: Int, budget: Int, revenue: Int, runtime: Int, status: String)

}