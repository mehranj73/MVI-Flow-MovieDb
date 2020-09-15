package com.mehranj73.moviedb.data.repository

import com.mehranj73.moviedb.util.*
import com.mehranj73.moviedb.util.Constants.CACHE_TIMEOUT
import com.mehranj73.moviedb.util.Constants.NETWORK_TIMEOUT
import com.mehranj73.moviedb.util.ErrorHandling.Companion.CACHE_ERROR_TIMEOUT
import com.mehranj73.moviedb.util.ErrorHandling.Companion.NETWORK_ERROR_TIMEOUT
import com.mehranj73.moviedb.util.ErrorHandling.Companion.UNKNOWN_ERROR
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException
import com.mehranj73.moviedb.util.ApiResult.*

/**
 * Reference: https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
 */
private val TAG: String = "AppDebug"

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T?
): ApiResult<T?> {
    return withContext(dispatcher) {
        try {
            // throws TimeoutCancellationException
            withTimeout(NETWORK_TIMEOUT){
                Success(apiCall.invoke())
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is TimeoutCancellationException -> {
                    val code = 408 // timeout error code
                    GenericError(code, NETWORK_ERROR_TIMEOUT)
                }
                is IOException -> {
                    NetworkError
                }
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    GenericError(
                        code,
                        errorResponse
                    )
                }
                else -> {
                    GenericError(
                        null,
                        UNKNOWN_ERROR
                    )
                }
            }
        }
    }
}

suspend fun <T> safeCacheCall(
    dispatcher: CoroutineDispatcher,
    cacheCall: suspend () -> T?
): CacheResult<T?> {
    return withContext(dispatcher) {
        try {
            // throws TimeoutCancellationException
            withTimeout(CACHE_TIMEOUT){
                CacheResult.Success(cacheCall.invoke())
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is TimeoutCancellationException -> {
                    CacheResult.GenericError(CACHE_ERROR_TIMEOUT)
                }
                else -> {
                    CacheResult.GenericError(UNKNOWN_ERROR)
                }
            }
        }
    }
}


fun <ViewState> buildError(
    message: String,
    uiComponentType: UIComponentType,
    stateEvent: StateEvent?
): DataState<ViewState> {
    return DataState.error(
        response = Response(
            message = "${stateEvent?.errorInfo()}\n\nReason: $message",
            uiComponentType = uiComponentType,
            messageType = MessageType.Error()
        ),
        stateEvent = stateEvent
    )

}

private fun convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.response()?.errorBody()?.string()
    } catch (exception: Exception) {
        UNKNOWN_ERROR
    }
}























