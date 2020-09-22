package com.mehranj73.moviedb.ui.trending.state

import com.mehranj73.moviedb.data.model.MovieEntity
import com.mehranj73.moviedb.data.model.TrendingEntity

data class TrendingViewState(
    var allTrending: List<TrendingEntity>? = null

)

