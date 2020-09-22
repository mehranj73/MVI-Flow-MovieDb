package com.mehranj73.moviedb.data.remote.response

import com.mehranj73.moviedb.data.model.MovieEntity


data class MovieResponse(
    val page: Int,
    val results: List<MovieEntity>,
    val total_pages: Int,
    val total_results: Int
)