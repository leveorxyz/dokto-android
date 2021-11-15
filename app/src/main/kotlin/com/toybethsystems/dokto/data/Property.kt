package com.toybethsystems.dokto.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

open class Property<T>(
    val state: MutableState<T?> = mutableStateOf(null),
    val error: MutableState<String?> = mutableStateOf(null)
)
