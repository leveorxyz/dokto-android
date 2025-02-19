package com.toybethsystems.dokto.base.data.model

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: BaseResponse?): ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()
}