package com.toybeth.docto.base.utils.extensions

import android.util.Patterns
import java.util.regex.Pattern

fun String?.isValidEmail(): Boolean {
    return !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun CharSequence?.isValidPassword(): Boolean {
    val passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"
    val pattern = Pattern.compile(passwordPattern)
    return !isNullOrEmpty() && pattern.matcher(this).matches()
}