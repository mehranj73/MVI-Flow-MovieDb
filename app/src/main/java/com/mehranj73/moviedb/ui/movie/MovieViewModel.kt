package com.mehranj73.moviedb.ui.movie

import androidx.hilt.lifecycle.ViewModelInject
import com.mehranj73.moviedb.data.model.Movie
import com.mehranj73.moviedb.data.repository.MovieRepository
import com.mehranj73.moviedb.ui.BaseViewModel
import com.mehranj73.moviedb.ui.movie.state.MovieStateEvent.MovieDetailEvent
import com.mehranj73.moviedb.ui.movie.state.MovieStateEvent.NowPlayingEvent
import com.mehranj73.moviedb.ui.movie.state.MovieViewState
import com.mehranj73.moviedb.util.*
import com.mehranj73.moviedb.util.ErrorHandling.Companion.INVALID_STATE_EVENT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


@ExperimentalCoroutinesApi
@FlowPreview
class MovieViewModel @ViewModelInject constructor(
    val movieRepository: MovieRepository
) : BaseViewModel<MovieViewState>() {

    override fun handleNewData(data: MovieViewState) {

        data.movies.let { movies ->
            movies?.let {
                setMoviesData(it)
            }
        }

        data.movieDetailFields.let { movieDetailFields ->
            movieDetailFields.movie?.let {
                setMovieDetail(it)
            }


        }

    }


    override fun setStateEvent(stateEvent: StateEvent) {

        val job: Flow<DataState<MovieViewState>> = when (stateEvent) {

            is NowPlayingEvent -> {
                movieRepository.getNowPlaying(
                    stateEvent = stateEvent
                )

            }

            is MovieDetailEvent -> {
                movieRepository.getMovieDetail(
                    stateEvent = stateEvent,
                    movieId = getMovieId()

                )
            }

            else -> {
                flow {
                    emit(
                        DataState.error<MovieViewState>(
                            response = Response(
                                message = INVALID_STATE_EVENT,
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


    private fun setMoviesData(movies: List<Movie>) {
        val update = getCurrentViewStateOrNew()
        update.movies = movies
        setViewState(update)
    }

    private fun setMovieDetail(movie: Movie) {
        val update = getCurrentViewStateOrNew()
        update.movieDetailFields.movie = movie
        setViewState(update)
    }


    override fun initNewViewState(): MovieViewState {
        return MovieViewState()
    }


    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }


    fun getMovieId(): Int {
        return getCurrentViewStateOrNew().movieDetailFields.movieId ?: 0
    }

    fun setMovieId(movieId: Int) {
        val update = getCurrentViewStateOrNew()
        val movieDetailFields = update.movieDetailFields
        movieDetailFields.movieId = movieId
        update.movieDetailFields = movieDetailFields
        setViewState(update)
    }


}