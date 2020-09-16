package com.mehranj73.moviedb.data.repository

import com.mehranj73.moviedb.ui.movie.state.MovieViewState
import com.mehranj73.moviedb.util.DataState
import com.mehranj73.moviedb.util.StateEvent
import kotlinx.coroutines.flow.Flow


interface MovieRepository {

    fun getNowPlaying(
        stateEvent: StateEvent
    ): Flow<DataState<MovieViewState>>

    fun getMovieId(
        movieId: Int,
        stateEvent: StateEvent
    ): Flow<DataState<MovieViewState>>
}