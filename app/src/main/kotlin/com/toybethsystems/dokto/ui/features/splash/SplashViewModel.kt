package com.toybethsystems.dokto.ui.features.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toybethsystems.dokto.base.ui.BaseViewModel
import com.toybethsystems.dokto.base.utils.SingleLiveEvent
import com.toybethsystems.dokto.base.utils.extensions.launchIOWithExceptionHandler
import com.toybethsystems.dokto.data.main.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    val navigateToLogin = MutableLiveData<Boolean>()
    val navigateToOnBoarding = SingleLiveEvent<Unit>()

    fun initialize() {
        viewModelScope.launchIOWithExceptionHandler({
            delay(500)
            if(repository.isFirstTimeUser()) {
                navigateToOnBoarding.postValue(Unit)
            } else {
                if(repository.isUserLoggedIn()) {
                    navigateToLogin.postValue(false)
                } else {
                    navigateToLogin.postValue(true)
                }
            }
        }, {
            it.printStackTrace()
        })
    }
}