package com.mehranj73.moviedb.data.repository

import android.util.Log
import com.mehranj73.moviedb.data.local.MovieDao
import com.mehranj73.moviedb.data.model.Movie
import com.mehranj73.moviedb.data.model.MovieResponse
import com.mehranj73.moviedb.data.remote.RetrofitService
import com.mehranj73.moviedb.ui.movie.state.MovieViewState
import com.mehranj73.moviedb.ui.movie.state.MovieViewState.MovieDetailFields
import com.mehranj73.moviedb.util.DataState
import com.mehranj73.moviedb.util.StateEvent
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "MovieRepositoryImpl"

@FlowPreview
class MovieRepositoryImpl @Inject constructor(
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

    override fun getMovieDetail(movieId: Int, stateEvent: StateEvent):
            Flow<DataState<MovieViewState>> =
        object : NetworkBoundResource<Movie, Movie, MovieViewState>(
            dispatcher = IO,
            stateEvent = stateEvent,
            apiCall = {
                Log.d(TAG, "getMovieId: called")
                retrofitService.getMovieDetail(movieId)
            },
            cacheCall = {
                Log.d(TAG, "getMovieDetail: cachcall")
                movieDao.getMovie(movieId)
            }

        ) {


            override suspend fun updateCache(networkObject: Movie) {

                withContext(IO){
                    movieDao.insert(networkObject)
                }
            }

            override fun handleCacheSuccess(resultObj: Movie): DataState<MovieViewState> {
                return DataState.data(
                    response = null,
                    data = MovieViewState(
                        movieDetailFields = MovieDetailFields(
                            movie = resultObj,
                            movieId = movieId
                        )
                    ),
                    stateEvent = stateEvent
                )
            }

        }.result


}