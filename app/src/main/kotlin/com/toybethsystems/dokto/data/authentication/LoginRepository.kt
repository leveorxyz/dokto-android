package com.toybethsystems.dokto.data.authentication

import com.toybethsystems.dokto.base.data.model.ResultWrapper
import com.toybethsystems.dokto.base.data.network.safeApiCall
import com.toybethsystems.dokto.base.data.preference.AppPreference
import com.toybethsystems.dokto.data.ApiService
import com.toybethsystems.dokto.data.authentication.model.LoginRequestBody
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiService: ApiService,
    private val preference: AppPreference
) {

    suspend fun login(email: String, password: String): Boolean {
        val requestBody = LoginRequestBody(email, password)
        return when (val response = safeApiCall { apiService.login(requestBody) }) {
            is ResultWrapper.Success -> {
                preference.user = response.value
                true
            }
            else -> false
        }
    }
}