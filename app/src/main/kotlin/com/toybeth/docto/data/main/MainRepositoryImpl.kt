package com.toybeth.docto.data.main

import com.orhanobut.logger.Logger
import com.toybeth.docto.base.data.preference.AppPreference
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val preference: AppPreference
) : MainRepository {

    override fun isUserLoggedIn(): Boolean {
        val user = preference.user
        Logger.d(user)
        return user.token.isNotEmpty()
    }

}