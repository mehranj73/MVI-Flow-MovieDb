package com.mehranj73.moviedb.data.remote.response


import com.mehranj73.moviedb.data.model.TvEntity

data class TvResponse(

    val id: Int,
    val page: Int,
    val results: List<TvEntity>,
    val total_pages: Int,
    val total_results: Int
)