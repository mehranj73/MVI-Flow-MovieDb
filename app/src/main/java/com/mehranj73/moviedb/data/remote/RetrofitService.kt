package com.mehranj73.moviedb.data.remote

import com.mehranj73.moviedb.data.model.Movie
import com.mehranj73.moviedb.data.model.MovieResponse
import com.mehranj73.moviedb.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String = API_KEY
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Movie




}