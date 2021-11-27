package com.toybeth.docto.ui.features.forgotpassword.enteremail

import com.toybeth.docto.base.ui.BaseViewModel
import com.toybeth.docto.base.utils.SingleLiveEvent
import com.toybeth.docto.base.utils.extensions.isEmailValid
import com.toybeth.docto.data.Property
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