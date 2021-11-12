package com.toybeth.docto.data

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Education(
    var college: College = College(),
    var courseStudied: CourseStudied = CourseStudied(),
    var graduationYear: GraduationYear = GraduationYear(),
    var speciality: Speciality = Speciality(),
    var certificate: Certificate = Certificate()
)

data class College(
    val state: MutableState<String> = mutableStateOf(""),
    val error: MutableState<String?> = mutableStateOf(null)
)

data class CourseStudied(
    val state: MutableState<String> = mutableStateOf(""),
    val error: MutableState<String?> = mutableStateOf(null)
)

data class GraduationYear(
    val state: MutableState<String> = mutableStateOf(""),
    val error: MutableState<String?> = mutableStateOf(null)
)

data class Speciality(
    val state: MutableState<String> = mutableStateOf(""),
    val error: MutableState<String?> = mutableStateOf(null)
)

data class Certificate(
    val state: MutableState<Bitmap?> = mutableStateOf(null),
    val error: MutableState<String?> = mutableStateOf(null)
)
