package com.toybeth.docto.ui.features.onboarding

import com.toybeth.docto.base.ui.BaseViewModel
import com.toybeth.docto.data.main.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationOnBoardingViewModel @Inject constructor(
    private val repository: MainRepository
): BaseViewModel() {

    fun onBoardingPassed() {
        repository.onBoardingPassed()
    }
}