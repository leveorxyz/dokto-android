package com.toybeth.docto.data

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Education(
    var college: MutableState<String> = mutableStateOf(""),
    var courseStudied: MutableState<String> = mutableStateOf(""),
    var graduationYear: MutableState<String> = mutableStateOf(""),
    var speciality: MutableState<String> = mutableStateOf(""),
    var certificateUri: Uri? = null
)
