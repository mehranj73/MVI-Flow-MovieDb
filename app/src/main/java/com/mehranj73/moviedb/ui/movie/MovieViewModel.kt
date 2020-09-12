package com.mehranj73.moviedb.ui.movie

import androidx.lifecycle.ViewModel
import com.mehranj73.moviedb.ui.BaseViewModel
import com.mehranj73.moviedb.ui.movie.state.MovieStateEvent
import com.mehranj73.moviedb.util.StateEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@ExperimentalCoroutinesApi
@FlowPreview
class MovieViewModel(

) : BaseViewModel<MovieStateEvent>() {
    override fun handleNewData(date: MovieStateEvent) {
        TODO("Not yet implemented")
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        TODO("Not yet implemented")
    }

    override fun initNewViewState(): MovieStateEvent {
        TODO("Not yet implemented")
    }

}