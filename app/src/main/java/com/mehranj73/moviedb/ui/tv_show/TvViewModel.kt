package com.mehranj73.moviedb.ui.tv_show

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.mehranj73.moviedb.ui.BaseViewModel
import com.mehranj73.moviedb.ui.tv_show.state.TvStateEvent
import com.mehranj73.moviedb.util.StateEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class TvViewModel @ViewModelInject constructor(): BaseViewModel<TvStateEvent>() {


    override fun handleNewData(date: TvStateEvent) {
        TODO("Not yet implemented")
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        TODO("Not yet implemented")
    }

    override fun initNewViewState(): TvStateEvent {
        TODO("Not yet implemented")
    }


}