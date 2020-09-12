package com.mehranj73.moviedb.data.model

data class MovieDetailResponse(
    val backdrop_path: String,
    val budget: Int,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
)