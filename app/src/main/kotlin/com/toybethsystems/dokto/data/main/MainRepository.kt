package com.toybethsystems.dokto.data.main

interface MainRepository {

    fun isFirstTimeUser(): Boolean

    fun isUserLoggedIn(): Boolean

    fun onBoardingPassed()

    fun logout()
}