package com.mehranj73.moviedb.ui.trending

import androidx.hilt.lifecycle.ViewModelInject
import com.mehranj73.moviedb.data.model.MovieEntity
import com.mehranj73.moviedb.data.model.TrendingEntity
import com.mehranj73.moviedb.data.repository.TrendingRepository
import com.mehranj73.moviedb.ui.BaseViewModel
import com.mehranj73.moviedb.ui.movie.state.MovieStateEvent
import com.mehranj73.moviedb.ui.movie.state.MovieViewState
import com.mehranj73.moviedb.ui.trending.state.TrendingStateEvent
import com.mehranj73.moviedb.ui.trending.state.TrendingViewState
import com.mehranj73.moviedb.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


@FlowPreview
@ExperimentalCoroutinesApi
class TrendingViewModel @ViewModelInject constructor(
    val trendingRepository: TrendingRepository
) : BaseViewModel<TrendingViewState>() {


    override fun handleNewData(data: TrendingViewState) {
        data.allTrending.let {allTrending ->
            allTrending?.let {
                setTrendingData(it)

            }
        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        if (!isJobAlreadyActive(stateEvent)) {
            val job: Flow<DataState<TrendingViewState>> = when (stateEvent) {

                is TrendingStateEvent.GetAllTrending -> {
                    trendingRepository.getAllTrending(
                        stateEvent = stateEvent
                    )

                }

                else -> {
                    flow {
                        emit(
                            DataState.error<TrendingViewState>(
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

    override fun initNewViewState(): TrendingViewState {
        return TrendingViewState()
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }


    private fun setTrendingData(allTrending: List<TrendingEntity>) {
        val update = getCurrentViewStateOrNew()
        update.allTrending = allTrending
        setViewState(update)
    }

}