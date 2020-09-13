package com.mehranj73.moviedb.ui.trending

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.mehranj73.moviedb.ui.BaseViewModel
import com.mehranj73.moviedb.ui.trending.state.TrendingStateEvent
import com.mehranj73.moviedb.util.StateEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@ExperimentalCoroutinesApi
class TrendingViewModel @ViewModelInject constructor() : BaseViewModel<TrendingStateEvent>() {
    override fun handleNewData(date: TrendingStateEvent) {
        TODO("Not yet implemented")
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        TODO("Not yet implemented")
    }

    override fun initNewViewState(): TrendingStateEvent {
        TODO("Not yet implemented")
    }


}