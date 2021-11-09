package com.toybeth.dokto.ui.features.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toybeth.dokto.base.ui.BaseViewModel
import com.toybeth.dokto.base.utils.extensions.launchIOWithExceptionHandler
import com.toybeth.dokto.data.main.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    val navigateToLogin = MutableLiveData<Boolean>()

    fun initialize() {
        viewModelScope.launchIOWithExceptionHandler({
            delay(500)
            navigateToLogin.postValue(!repository.isUserLoggedIn())
        }, {
            it.printStackTrace()
        })
    }
}