package com.toybeth.docto.ui.features.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orhanobut.logger.Logger
import com.toybeth.docto.base.ui.BaseViewModel
import com.toybeth.docto.base.utils.SingleLiveEvent
import com.toybeth.docto.base.utils.extensions.launchIOWithExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    private val mutableInitializeLoginForm = MutableLiveData<Boolean>()
    private val mutableInitializeLoginScreen = MutableLiveData<Boolean>(true)
    private val userNameOrPhoneErrorMutableLiveData = MutableLiveData<Boolean>()
    private val passwordErrorMutableLiveData = MutableLiveData<Boolean>()

    val userNameOrPhone = mutableStateOf("")
    val password = mutableStateOf("")
    val navigateToForgetPasswordFlow = SingleLiveEvent<Boolean>()
    val navigateToRegistrationFlow = SingleLiveEvent<Boolean>()

    val initializeLoginForm: LiveData<Boolean>
        get() = mutableInitializeLoginForm
    val initializeLoginScreen: LiveData<Boolean>
        get() = mutableInitializeLoginScreen
    val userNameOrPhoneError: LiveData<Boolean>
        get() = userNameOrPhoneErrorMutableLiveData
    val passwordError: LiveData<Boolean>
        get() = passwordErrorMutableLiveData

    fun submit() {
        validateLoginForm()
    }

    private fun validateLoginForm() {
        if(userNameOrPhone.value.isNullOrEmpty()) {

        }
    }

    fun navigateToForgetPassword() {
        viewModelScope.launchIOWithExceptionHandler({
            navigateToForgetPasswordFlow.postValue(true)
        }, {
            it.printStackTrace()
        })
    }

    fun navigateToRegistration() {
        viewModelScope.launchIOWithExceptionHandler({
            exitFromLoginScreen()
            delay(50)
            navigateToRegistrationFlow.postValue(true)
        }, {
            it.printStackTrace()
        })
    }

    fun exitFromLoginScreen() {
        viewModelScope.launch {
            mutableInitializeLoginScreen.postValue(false)
        }
    }

    fun enterToLoginScreen() {
        viewModelScope.launch {
            mutableInitializeLoginForm.postValue(true)
            if(mutableInitializeLoginScreen.value == false) {
                mutableInitializeLoginScreen.postValue(true)
            }
        }
    }

}