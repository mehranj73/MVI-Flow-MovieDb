package com.mehranj73.moviedb.ui.tv_show

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.mehranj73.moviedb.data.model.MovieEntity
import com.mehranj73.moviedb.data.model.TvEntity
import com.mehranj73.moviedb.data.repository.TvRepository
import com.mehranj73.moviedb.ui.BaseViewModel
import com.mehranj73.moviedb.ui.movie.state.MovieStateEvent
import com.mehranj73.moviedb.ui.movie.state.MovieViewState
import com.mehranj73.moviedb.ui.tv_show.state.TvStateEvent
import com.mehranj73.moviedb.ui.tv_show.state.TvStateEvent.TvAiringTodayEvent
import com.mehranj73.moviedb.ui.tv_show.state.TvStateEvent.TvDetailEvent
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
        data.tvDetailFields.let {tvDetailFields ->
            tvDetailFields.tvEntity?.let {
                setMovieDetail(it)
            }

        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        if (!isJobAlreadyActive(stateEvent)) {
            val job: Flow<DataState<TvViewState>> = when (stateEvent) {

                is TvAiringTodayEvent -> {
                    tvRepository.getTvAiringToday(
                        stateEvent = stateEvent
                    )

                }

                is TvDetailEvent -> {
                    tvRepository.getTvDetail(
                        stateEvent = stateEvent,
                        tvId = getTvId()
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

    fun setTvId(tvId: Int) {
        val update = getCurrentViewStateOrNew()
        val tvDetailFields = update.tvDetailFields
        tvDetailFields.tvId = tvId
        update.tvDetailFields = tvDetailFields
        setViewState(update)
    }

    fun getTvId(): Int {
        return getCurrentViewStateOrNew().tvDetailFields.tvId ?: 0
    }


    private fun setMovieDetail(tvEntity: TvEntity) {
        val update = getCurrentViewStateOrNew()
        update.tvDetailFields.tvEntity = tvEntity
        setViewState(update)
    }

}