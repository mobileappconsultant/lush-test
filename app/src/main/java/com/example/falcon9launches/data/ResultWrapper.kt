package com.example.falcon9launches.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()

    data class GenericError(
        val code: Int? = null,
        val error: ErrorResponse? = null
    ) : ResultWrapper<Nothing>()

    object NetworkError : ResultWrapper<Nothing>()
}

data class ErrorResponse(val errorDescription: String, val errorBody: ErrorBody? = null)

data class ErrorBody(val code: Int, val message: String)

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResultWrapper.GenericError(code, errorResponse)
                }
                else -> ResultWrapper.GenericError(null, null)
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    runCatching {
        throwable.message()
    }.onSuccess { throwableMessage ->

        var body: JSONObject? = null
        runCatching {
            throwable.response()?.errorBody()?.let { JSONObject(it.string()) }
        }.onSuccess { result ->
            body = result
        }

        val errorBody = body?.let {
            ErrorBody(
                it.optInt("errorCode", -1),
                it.optString("errorMessage", "")
            )
        }
        return ErrorResponse(throwableMessage, errorBody)
    }

    return null
}