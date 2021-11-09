package com.toybeth.docto.ui.features.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orhanobut.logger.Logger
import com.toybeth.docto.base.ui.BaseViewModel
import com.toybeth.docto.base.utils.SingleLiveEvent
import com.toybeth.docto.base.utils.extensions.isEmailValid
import com.toybeth.docto.base.utils.extensions.isPasswordValid
import com.toybeth.docto.base.utils.extensions.launchIOWithExceptionHandler
import com.toybeth.docto.data.authentication.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : BaseViewModel() {

    private val mutableInitializeLoginForm = MutableLiveData<Boolean>()
    private val mutableInitializeLoginScreen = MutableLiveData<Boolean>(true)
    private val userNameOrPhoneErrorMutableLiveData = MutableLiveData<Boolean>()
    private val passwordErrorMutableLiveData = MutableLiveData<Boolean>()
    private val loginSuccessfulMutableLiveData = MutableLiveData<Pair<Boolean, String?>>()

    val userNameOrPhone = mutableStateOf("")
    val password = mutableStateOf("")
    val navigateToForgetPasswordFlow = SingleLiveEvent<Boolean>()

    val initializeLoginForm: LiveData<Boolean>
        get() = mutableInitializeLoginForm
    val initializeLoginScreen: LiveData<Boolean>
        get() = mutableInitializeLoginScreen
    val userNameOrPhoneError: LiveData<Boolean>
        get() = userNameOrPhoneErrorMutableLiveData
    val passwordError: LiveData<Boolean>
        get() = passwordErrorMutableLiveData
    val loginSuccessful: LiveData<Pair<Boolean, String?>>
        get() = loginSuccessfulMutableLiveData

    fun submit() {
        if(validateLoginForm()) {
            viewModelScope.launchIOWithExceptionHandler({
                loader.postValue(true)
                val isLogingSuccessful = repository.login(userNameOrPhone.value, password.value)
                loginSuccessfulMutableLiveData.postValue(Pair(isLogingSuccessful, null))
                loader.postValue(false)
            }, {
                it.printStackTrace()
                loginSuccessfulMutableLiveData.postValue(Pair(false, it.localizedMessage))
            })
        }
    }

    private fun validateLoginForm(): Boolean {
        if(!userNameOrPhone.value.isEmailValid()) {
            userNameOrPhoneErrorMutableLiveData.value = true
            return false
        } else if(!password.value.isPasswordValid()) {
            passwordErrorMutableLiveData.value = true
            return false
        } else {
            userNameOrPhoneErrorMutableLiveData.value = false
            passwordErrorMutableLiveData.value = false
            return true
        }
    }

    fun navigateToForgetPassword() {
        viewModelScope.launchIOWithExceptionHandler({
            exitFromLoginScreen()
            delay(50)
            navigateToForgetPasswordFlow.postValue(true)
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