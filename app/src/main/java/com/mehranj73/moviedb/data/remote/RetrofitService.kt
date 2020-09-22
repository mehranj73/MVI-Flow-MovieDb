package com.mehranj73.moviedb.data.remote

import com.mehranj73.moviedb.data.model.MovieEntity
import com.mehranj73.moviedb.data.remote.response.MovieResponse
import com.mehranj73.moviedb.data.model.TrendingEntity
import com.mehranj73.moviedb.data.remote.response.TrendingResponse
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
    ): MovieEntity


    @GET("trending/all/day")
    suspend fun getAllTrending(
        @Query("api_key") apiKey: String = API_KEY
    ): TrendingResponse




}