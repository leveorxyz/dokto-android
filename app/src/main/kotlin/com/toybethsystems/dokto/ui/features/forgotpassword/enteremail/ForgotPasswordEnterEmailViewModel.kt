package com.toybethsystems.dokto.ui.features.forgotpassword.enteremail

import com.toybethsystems.dokto.base.ui.BaseViewModel
import com.toybethsystems.dokto.base.utils.SingleLiveEvent
import com.toybethsystems.dokto.base.utils.extensions.isEmailValid
import com.toybethsystems.dokto.data.Property
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordEnterEmailViewModel @Inject constructor() : BaseViewModel() {

    val email = Property<String>()
    val navigateToOtp = SingleLiveEvent<Boolean>()

    private fun verifyEmail(): Boolean {
        var isValid = true

        if (email.state.value.isNullOrEmpty()) {
            email.error.value = "This field is required"
            isValid = false
        }

        if (!email.state.value.isEmailValid()) {
            email.error.value = "Not valid email"
            isValid = false
        }

        return isValid
    }

    fun sendOtp() {
        if (verifyEmail()) {
            // TODO: Send OTP to email
            navigateToOtp.postValue(true)
        }
    }
}