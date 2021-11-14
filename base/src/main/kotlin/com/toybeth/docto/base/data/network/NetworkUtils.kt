package com.toybeth.docto.base.data.network

import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.toybeth.docto.base.data.model.ApiResponse
import com.toybeth.docto.base.data.model.BaseResponse
import com.toybeth.docto.base.data.model.ResultWrapper
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

fun <T> Single<Response<T>>.onResponse(): Single<T> {
    return map {
        if (it.isSuccessful) {
            if (it.body() != null) {
                it.body()
            } else {
                throw Exception("Request Exception")
            }
        } else {
            throw Exception(it.message())
        }
    }
}

fun <T> Observable<Response<T>>.onResponse(): Observable<T> {
    return map {
        if (it.isSuccessful) {
            if (it.body() != null) {
                it.body()
            } else {
                throw Exception("Request Exception")
            }
        } else {
            throw Exception(it.message())
        }
    }
}

fun <T> Flowable<Response<T>>.onResponse(): Flowable<T> {
    return map {
        if (it.isSuccessful) {
            if (it.body() != null) {
                it.body()
            } else {
                throw Exception("Request Exception")
            }
        } else {
            throw Exception(it.message())
        }
    }
}

fun <T> Maybe<Response<T>>.onResponse(): Maybe<T> {
    return map {
        if (it.isSuccessful) {
            if (it.body() != null) {
                it.body()
            } else {
                throw Exception("Request Exception")
            }
        } else {
            throw Exception(it.message())
        }
    }
}

suspend fun <T>safeApiCall(apiCall: suspend () -> Response<ApiResponse<T>>): ResultWrapper<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall.invoke()
            if(response.isSuccessful && response.body() != null) {
                ResultWrapper.Success(response.body()!!.result)
            } else {
                throw HttpException(response)
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    convertErrorBody(throwable)
                }
                else -> {
                    ResultWrapper.GenericError(null, null)
                }
            }
        }
    }
}

private fun <T>convertErrorBody(throwable: HttpException): ResultWrapper<T> {
    return try {
        throwable.response()?.errorBody()?.string().let {
            val response = Gson().fromJson(it, BaseResponse::class.java)
            Logger.d(response.message)
            ResultWrapper.GenericError(throwable.code(), response)
        }

    } catch (exception: Exception) {
        ResultWrapper.GenericError(null, null)
    }
}