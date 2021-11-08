package com.toybeth.docto.ui.features.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toybeth.docto.base.ui.BaseViewModel
import com.toybeth.docto.base.utils.extensions.launchIOWithExceptionHandler
import com.toybeth.docto.data.main.MainRepository
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