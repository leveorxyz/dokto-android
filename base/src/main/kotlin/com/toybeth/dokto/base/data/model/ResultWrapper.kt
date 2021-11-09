package com.toybeth.dokto.base.data.model

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): com.toybeth.dokto.base.data.model.ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: com.toybeth.dokto.base.data.model.BaseResponse?): com.toybeth.dokto.base.data.model.ResultWrapper<Nothing>()
    object NetworkError: com.toybeth.dokto.base.data.model.ResultWrapper<Nothing>()
}