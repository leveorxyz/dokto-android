package com.toybeth.dokto.data.authentication

import com.orhanobut.logger.Logger
import com.toybeth.dokto.base.data.model.ResultWrapper
import com.toybeth.dokto.base.data.network.safeApiCall
import com.toybeth.dokto.base.data.preference.AppPreference
import com.toybeth.dokto.data.ApiService
import com.toybeth.dokto.data.authentication.model.LoginRequestBody
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiService: ApiService,
    private val preference: AppPreference
) {

    suspend fun login(email: String, password: String): Boolean {
        val requestBody = LoginRequestBody(email, password)
        return when (val response = safeApiCall { apiService.login(requestBody) }) {
            is com.toybeth.dokto.base.data.model.ResultWrapper.Success -> {
                Logger.d(response.value)
                preference.user = response.value
                true
            }
            else -> false
        }
    }
}