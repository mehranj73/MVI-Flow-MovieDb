package com.mehranj73.moviedb.data.repository

import com.mehranj73.moviedb.data.local.MovieDao
import com.mehranj73.moviedb.data.model.Movie
import com.mehranj73.moviedb.data.model.MovieResponse
import com.mehranj73.moviedb.data.remote.RetrofitService
import com.mehranj73.moviedb.ui.movie.state.MovieViewState
import com.mehranj73.moviedb.util.DataState
import com.mehranj73.moviedb.util.StateEvent
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    val retrofitService: RetrofitService,
    val movieDao: MovieDao
) : MovieRepository {

    @FlowPreview
    override fun getNowPlaying(
        stateEvent: StateEvent
    ): Flow<DataState<MovieViewState>> =
        object : NetworkBoundResource<MovieResponse, List<Movie>, MovieViewState>(
            dispatcher = IO,
            stateEvent = stateEvent,
            apiCall = {
                retrofitService.getNowPlaying()
            },
            cacheCall = {
                movieDao.getMovies()
            }
        ) {

            override suspend fun updateCache(networkObject: MovieResponse) {
                val movies = networkObject.results
                withContext(IO) {
                    launch {
                        movieDao.insertList(movies)
                    }

                }

            }

            override fun handleCacheSuccess(resultObj: List<Movie>): DataState<MovieViewState> {
                return DataState.data(
                    response = null,
                    data = MovieViewState(
                        movies = resultObj
                    ),
                    stateEvent = stateEvent
                )
            }

        }.result


}