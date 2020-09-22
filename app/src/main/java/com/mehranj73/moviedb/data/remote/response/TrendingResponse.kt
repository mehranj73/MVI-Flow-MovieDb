package com.mehranj73.moviedb.data.remote.response

import com.mehranj73.moviedb.data.model.TrendingEntity

data class TrendingResponse(
    val page: Int,
    val results: List<TrendingEntity>,
    val total_pages: Int,
    val total_results: Int
)