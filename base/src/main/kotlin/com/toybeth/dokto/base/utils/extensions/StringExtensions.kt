package com.toybeth.dokto.base.utils.extensions

import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern

fun String.isEmailValid(): Boolean {
    return isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isPasswordValid(): Boolean {
    val passwordRegex = "^(?=.*[0-9])(?=.*[a-z]).{8,20}$"
    val pattern: Pattern = Pattern.compile(passwordRegex)
    val matcher: Matcher = pattern.matcher(this)
    return matcher.matches()
}