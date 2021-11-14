package com.toybeth.docto.data.main

interface MainRepository {

    fun isFirstTimeUser(): Boolean

    fun isUserLoggedIn(): Boolean

    fun onBoardingPassed()
}