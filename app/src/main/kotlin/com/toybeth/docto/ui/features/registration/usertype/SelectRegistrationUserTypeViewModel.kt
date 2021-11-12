package com.toybeth.docto.ui.features.registration.usertype

import com.toybeth.docto.base.ui.BaseViewModel
import com.toybeth.docto.base.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectRegistrationUserTypeViewModel @Inject constructor(): BaseViewModel() {

    var navigateToDoctorRegistration = SingleLiveEvent<Boolean>()

    fun navigateToNextPage(isDoctorSelected: Boolean) {
        navigateToDoctorRegistration.postValue(isDoctorSelected)
    }
}