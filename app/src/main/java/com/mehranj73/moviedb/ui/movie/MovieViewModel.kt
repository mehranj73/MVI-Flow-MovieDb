package com.mehranj73.moviedb.ui.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.mehranj73.moviedb.data.model.Movie
import com.mehranj73.moviedb.data.repository.MovieRepository
import com.mehranj73.moviedb.ui.BaseViewModel
import com.mehranj73.moviedb.ui.movie.state.MovieStateEvent
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
                setMoviesData(movies)
            }


        }

    }


    override fun setStateEvent(stateEvent: StateEvent) {

        val job: Flow<DataState<MovieViewState>> = when(stateEvent){

                is MovieStateEvent -> {
                    movieRepository.getNowPlaying(
                        stateEvent = stateEvent
                    )

                }

            else -> {
                flow {
                    emit(
                        DataState.error <MovieViewState>(
                            response = Response(
                                message = INVALID_STATE_EVENT,
                                uiComponentType = UIComponentType.None(),
                                messageType = MessageType.Error()
                            ),
                            stateEvent = stateEvent
                        )
                    )
                }
            }
        }

    }


    fun setMoviesData(movies: List<Movie>){
        val update = getCurrentViewStateOrNew()
        if(update.movies == movies){
            return
        }
        update.movies = movies
        setViewState(update)
    }



    override fun initNewViewState(): MovieViewState {
        return MovieViewState()
    }


    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }


}