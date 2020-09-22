package com.mehranj73.moviedb.data.repository

import com.mehranj73.moviedb.data.local.MovieDao
import com.mehranj73.moviedb.data.model.MovieEntity
import com.mehranj73.moviedb.data.remote.response.MovieResponse
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
        object : NetworkBoundResource<MovieResponse, List<MovieEntity>, MovieViewState>(
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

            override fun handleCacheSuccess(resultObj: List<MovieEntity>): DataState<MovieViewState> {
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
        object : NetworkBoundResource<MovieEntity, MovieEntity, MovieViewState>(
            dispatcher = IO,
            stateEvent = stateEvent,
            apiCall = {

                retrofitService.getMovieDetail(movieId)
            },
            cacheCall = {

                movieDao.getMovie(movieId)
            }

        ) {


            override suspend fun updateCache(networkObject: MovieEntity) {

                withContext(IO){
                    movieDao.insert(networkObject)
                }
            }

            override fun handleCacheSuccess(resultObj: MovieEntity): DataState<MovieViewState> {
                return DataState.data(
                    response = null,
                    data = MovieViewState(
                        movieDetailFields = MovieDetailFields(
                            movieEntity = resultObj,
                            movieId = movieId
                        )
                    ),
                    stateEvent = stateEvent
                )
            }

        }.result


}