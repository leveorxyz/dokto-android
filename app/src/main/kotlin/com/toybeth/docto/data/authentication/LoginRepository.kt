package com.toybeth.docto.data.authentication

import com.orhanobut.logger.Logger
import com.toybeth.docto.base.data.model.ResultWrapper
import com.toybeth.docto.base.data.network.safeApiCall
import com.toybeth.docto.base.data.preference.AppPreference
import com.toybeth.docto.data.ApiService
import com.toybeth.docto.data.authentication.model.LoginRequestBody
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiService: ApiService,
    private val preference: AppPreference
) {

    suspend fun login(email: String, password: String): Boolean {
        val requestBody = LoginRequestBody(email, password)
        return when (val response = safeApiCall { apiService.login(requestBody) }) {
            is ResultWrapper.Success -> {
                Logger.d(response.value)
                preference.user = response.value
                true
            }
            else -> false
        }
    }
}