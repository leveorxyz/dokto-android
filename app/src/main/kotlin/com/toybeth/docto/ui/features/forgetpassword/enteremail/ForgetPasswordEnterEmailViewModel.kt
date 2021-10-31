package com.toybeth.docto.ui.features.forgetpassword.enteremail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toybeth.docto.base.ui.BaseViewModel
import com.toybeth.docto.base.utils.SingleLiveEvent
import com.toybeth.docto.base.utils.extensions.launchIOWithExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordEnterEmailViewModel @Inject constructor(): BaseViewModel() {

    private val screenVisibleMutableLiveData = MutableLiveData(false)
    val navigateToOtp = SingleLiveEvent<Boolean>()
    val screenVisible: LiveData<Boolean>
        get() = screenVisibleMutableLiveData

    fun sendOtp(email: String) {
        screenVisibleMutableLiveData.postValue(false)
        navigateToOtp.postValue(true)
    }

    fun enterToScreen() {
        viewModelScope.launchIOWithExceptionHandler({
            screenVisibleMutableLiveData.postValue(true)
        }, {
            it.printStackTrace()
        })
    }
}