package com.mehranj73.moviedb.data.repository

import com.mehranj73.moviedb.data.local.TrendingDao
import com.mehranj73.moviedb.data.model.TrendingEntity
import com.mehranj73.moviedb.data.remote.RetrofitService
import com.mehranj73.moviedb.data.remote.response.TrendingResponse
import com.mehranj73.moviedb.ui.trending.state.TrendingViewState
import com.mehranj73.moviedb.util.DataState
import com.mehranj73.moviedb.util.StateEvent
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


@FlowPreview
class TrendingRepositoryImpl @Inject constructor(
    val retrofitService: RetrofitService,
    val trendingDao: TrendingDao

) : TrendingRepository {

    override fun getAllTrending(
        stateEvent: StateEvent
    ): Flow<DataState<TrendingViewState>> =

        object : NetworkBoundResource<TrendingResponse, List<TrendingEntity>, TrendingViewState>(
            IO,
            stateEvent = stateEvent,
            apiCall = {
                retrofitService.getAllTrending()
            },
            cacheCall = {
                trendingDao.getAllTrending()
            }
        ) {

            override suspend fun updateCache(networkObject: TrendingResponse) {
                withContext(IO) {
                   if (!networkObject.results.isNullOrEmpty()){
                       trendingDao.nukeTrendingTable()
                       trendingDao.insertList(networkObject.results)
                   }


                }
            }

            override fun handleCacheSuccess(resultObj: List<TrendingEntity>): DataState<TrendingViewState> {
                return DataState.data(
                    response = null,
                    data = TrendingViewState(
                        allTrending = resultObj
                    ),
                    stateEvent = stateEvent

                )
            }

        }.result


}