package com.toybethsystems.dokto.base.utils.extensions

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun CoroutineScope.launchIOWithExceptionHandler(block: suspend CoroutineScope.() -> Unit, onError: (Throwable) -> Unit) {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->  onError(throwable)}
    launch(Dispatchers.IO + exceptionHandler) {
        try {
            block()
        } catch (e: Exception) {
            onError(e)
        }
    }
}