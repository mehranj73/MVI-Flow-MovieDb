package com.mehranj73.moviedb.data.repository

import com.mehranj73.moviedb.ui.trending.state.TrendingViewState
import com.mehranj73.moviedb.util.DataState
import com.mehranj73.moviedb.util.StateEvent
import kotlinx.coroutines.flow.Flow

interface TrendingRepository {

    fun getAllTrending(
        stateEvent: StateEvent
    ): Flow<DataState<TrendingViewState>>
}