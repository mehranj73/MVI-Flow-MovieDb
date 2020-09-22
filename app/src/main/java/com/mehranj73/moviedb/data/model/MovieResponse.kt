package com.mehranj73.moviedb.data.model



data class MovieResponse(
    val page: Int,
    val results: List<MovieEntity>,
    val total_pages: Int,
    val total_results: Int
)