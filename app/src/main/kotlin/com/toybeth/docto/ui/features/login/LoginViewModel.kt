package com.toybeth.docto.ui.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toybeth.docto.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    private val mutableInitializeLogin = MutableLiveData<Boolean>()
    val initializeLogin: LiveData<Boolean>
        get() = mutableInitializeLogin

    init {
        initialize()
    }

    private fun initialize() {
        viewModelScope.launch {
            delay(500)
            mutableInitializeLogin.postValue(true)
        }
    }

}