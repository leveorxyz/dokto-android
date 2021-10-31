package com.toybeth.docto.ui.features.forgetpassword.enteremail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toybeth.docto.base.ui.BaseViewModel
import com.toybeth.docto.base.utils.SingleLiveEvent
import com.toybeth.docto.base.utils.extensions.launchIOWithExceptionHandler
import com.toybeth.docto.base.ui.uiutils.AnimState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordEnterEmailViewModel @Inject constructor(): BaseViewModel() {

    val navigateToOtp = SingleLiveEvent<Boolean>()

    fun sendOtp(email: String) {
        navigateToOtp.postValue(true)
    }
}