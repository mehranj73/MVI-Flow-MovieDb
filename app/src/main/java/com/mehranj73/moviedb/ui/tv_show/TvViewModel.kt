package com.mehranj73.moviedb.ui.tv_show

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.mehranj73.moviedb.data.model.MovieEntity
import com.mehranj73.moviedb.data.model.TvEntity
import com.mehranj73.moviedb.data.repository.TvRepository
import com.mehranj73.moviedb.ui.BaseViewModel
import com.mehranj73.moviedb.ui.movie.state.MovieStateEvent
import com.mehranj73.moviedb.ui.movie.state.MovieViewState
import com.mehranj73.moviedb.ui.tv_show.state.TvStateEvent
import com.mehranj73.moviedb.ui.tv_show.state.TvViewState
import com.mehranj73.moviedb.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@FlowPreview
@ExperimentalCoroutinesApi
class TvViewModel @ViewModelInject constructor(
    val tvRepository: TvRepository
): BaseViewModel<TvViewState>() {


    override fun handleNewData(data: TvViewState) {

        data.tvs.let {tvs ->
            tvs?.let {
                setTvsData(it)
            }

        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        if (!isJobAlreadyActive(stateEvent)) {
            val job: Flow<DataState<TvViewState>> = when (stateEvent) {

                is TvStateEvent.TvAiringTodayEvent -> {
                    tvRepository.getTvAiringToday(
                        stateEvent = stateEvent
                    )

                }


                else -> {
                    flow {
                        emit(
                            DataState.error<TvViewState>(
                                response = Response(
                                    message = ErrorHandling.INVALID_STATE_EVENT,
                                    uiComponentType = UIComponentType.Toast(),
                                    messageType = MessageType.Error()
                                ),
                                stateEvent = stateEvent
                            )
                        )
                    }
                }
            }
            launchJob(stateEvent, job)
        }
    }

    override fun initNewViewState(): TvViewState {
        return TvViewState()
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }


    private fun setTvsData(tvs: List<TvEntity>) {
        val update = getCurrentViewStateOrNew()
        update.tvs = tvs
        setViewState(update)
    }
}