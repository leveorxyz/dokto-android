package com.toybethsystems.dokto.ui.features.forgetpassword.enteremail

import com.toybethsystems.dokto.base.ui.BaseViewModel
import com.toybethsystems.dokto.base.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordEnterEmailViewModel @Inject constructor() : BaseViewModel() {

    val navigateToOtp = SingleLiveEvent<Boolean>()

    fun sendOtp(email: String) {
        navigateToOtp.postValue(true)
    }
}