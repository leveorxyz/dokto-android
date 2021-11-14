package com.toybeth.docto.data.main

import com.toybeth.docto.base.data.preference.AppPreference
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val preference: AppPreference
) : MainRepository {

    override fun isFirstTimeUser(): Boolean {
        return preference.isFirstTimeUser
    }

    override fun isUserLoggedIn(): Boolean {
        return preference.user.token.isEmpty()
    }

    override fun onBoardingPassed() {
        preference.isFirstTimeUser = false
    }

}