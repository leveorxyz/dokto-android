package com.toybeth.dokto.ui.features.forgetpassword.enteremail

import com.toybeth.dokto.base.ui.BaseViewModel
import com.toybeth.dokto.base.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordEnterEmailViewModel @Inject constructor() : BaseViewModel() {

    val navigateToOtp = SingleLiveEvent<Boolean>()

    fun sendOtp(email: String) {
        navigateToOtp.postValue(true)
    }
}