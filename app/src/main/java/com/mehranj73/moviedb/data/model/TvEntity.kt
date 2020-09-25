package com.mehranj73.moviedb.data.model

data class TvEntity(
    val backdrop_path: String?,
    val first_air_date: String,
    val id: Int,
    val name: String,

    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val vote_average: Double,
    val vote_count: Int
)