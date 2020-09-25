package com.mehranj73.moviedb.data.repository

import com.mehranj73.moviedb.data.local.TvDao
import com.mehranj73.moviedb.data.model.TvEntity
import com.mehranj73.moviedb.data.remote.RetrofitService
import com.mehranj73.moviedb.data.remote.response.TvResponse
import com.mehranj73.moviedb.ui.tv_show.state.TvViewState
import com.mehranj73.moviedb.util.DataState
import com.mehranj73.moviedb.util.StateEvent
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@FlowPreview
class TvRepositoryImpl @Inject constructor(
    val retrofitService: RetrofitService,
    val tvDao: TvDao
) : TvRepository {

    override fun getTvAiringToday(stateEvent: StateEvent): Flow<DataState<TvViewState>> =
        object : NetworkBoundResource<TvResponse, List<TvEntity>, TvViewState>(
            IO,
            stateEvent,
            apiCall = {
                retrofitService.getTvAiringToday()

            },
            cacheCall = {
                tvDao.getTvs()
            }
        ) {
            override suspend fun updateCache(networkObject: TvResponse) {
                val tvs = networkObject.results
                withContext(IO) {
                    launch {
                        tvDao.insertList(tvs)
                    }

                }
            }

            override fun handleCacheSuccess(resultObj: List<TvEntity>): DataState<TvViewState> {
                return DataState.data(
                    response = null,
                    data = TvViewState(
                        tvs = resultObj
                    ),
                    stateEvent = stateEvent
                )
            }

        }.result


    override fun getTvDetail(tvId: Int, stateEvent: StateEvent): Flow<DataState<TvViewState>> =
        object : NetworkBoundResource<TvEntity, TvEntity, TvViewState>(
            IO,
            stateEvent,
            apiCall = {
                retrofitService.getTvDetail(tvId)
            },
            cacheCall = {
                tvDao.getTv(tvId)
            }
        ) {
            override suspend fun updateCache(networkObject: TvEntity) {

                withContext(IO) {
                    launch {
                        tvDao.insert(networkObject)
                    }

                }
            }

            override fun handleCacheSuccess(resultObj: TvEntity): DataState<TvViewState> {
                return DataState.data(
                    response = null,
                    data = TvViewState(
                        tvs = null,
                        tvDetailFields = TvViewState.TvDetailFields(
                            tvEntity = resultObj,
                            tvId = tvId
                        )
                    ),
                    stateEvent = stateEvent
                )
            }

        }.result

}

