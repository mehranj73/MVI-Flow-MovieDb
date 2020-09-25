package com.mehranj73.moviedb.data.repository

import com.mehranj73.moviedb.ui.movie.state.MovieViewState
import com.mehranj73.moviedb.ui.tv_show.state.TvViewState
import com.mehranj73.moviedb.util.DataState
import com.mehranj73.moviedb.util.StateEvent
import kotlinx.coroutines.flow.Flow

interface TvRepository {

    fun getTvAiringToday(
        stateEvent: StateEvent
    ):Flow<DataState<TvViewState>>
}