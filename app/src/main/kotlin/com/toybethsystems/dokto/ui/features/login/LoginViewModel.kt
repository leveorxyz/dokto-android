package com.toybethsystems.dokto.ui.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toybethsystems.dokto.base.ui.BaseViewModel
import com.toybethsystems.dokto.base.utils.SingleLiveEvent
import com.toybethsystems.dokto.base.utils.extensions.isEmailValid
import com.toybethsystems.dokto.base.utils.extensions.isPasswordValid
import com.toybethsystems.dokto.base.utils.extensions.launchIOWithExceptionHandler
import com.toybethsystems.dokto.data.Property
import com.toybethsystems.dokto.data.authentication.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : BaseViewModel() {

    private val mutableInitializeLoginForm = MutableLiveData<Boolean>()
    private val mutableInitializeLoginScreen = MutableLiveData(true)

    private val loginSuccessfulMutableLiveData = MutableLiveData<Pair<Boolean, String?>>()

    val userNameOrPhone = Property<String>()
    val password = Property<String>()
    val navigateToForgetPasswordFlow = SingleLiveEvent<Boolean>()
    val navigateToRegistrationFlow = SingleLiveEvent<Boolean>()

    val initializeLoginForm: LiveData<Boolean>
        get() = mutableInitializeLoginForm
    val initializeLoginScreen: LiveData<Boolean>
        get() = mutableInitializeLoginScreen

    val loginSuccessful: LiveData<Pair<Boolean, String?>>
        get() = loginSuccessfulMutableLiveData

    fun submit() {
        if (validateLoginForm()) {
            loader.postValue(true)
            viewModelScope.launchIOWithExceptionHandler({
                repository.login(userNameOrPhone.state.value!!, password.state.value!!)
                loader.postValue(false)
                loginSuccessfulMutableLiveData.postValue(Pair(true, null))
            }, {
                loader.postValue(false)
                it.printStackTrace()
                loginSuccessfulMutableLiveData.postValue(Pair(false, it.localizedMessage))
            })
        }
    }

    private fun validateLoginForm(): Boolean {
        var isValid = true
        if (userNameOrPhone.state.value.isNullOrEmpty()) {
            userNameOrPhone.error.value = "This field is required"
            isValid = false
        } else if (!userNameOrPhone.state.value.isEmailValid()) {
            userNameOrPhone.error.value = "Enter a valid email"
            isValid = false
        }

        if (password.state.value.isNullOrEmpty()) {
            password.error.value = "This field is required"
            isValid = false
        } else if (!password.state.value.isPasswordValid()) {
            password.error.value =
                "Password must be 8 characters long and must have minimum 1 number and 1 letter"
            isValid = false
        }

        return isValid
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
            if (mutableInitializeLoginScreen.value == false) {
                mutableInitializeLoginScreen.postValue(true)
            }
        }
    }

}