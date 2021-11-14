package com.toybeth.docto.ui.features.registration.usertype

import com.toybeth.docto.base.ui.BaseViewModel
import com.toybeth.docto.base.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectRegistrationUserTypeViewModel @Inject constructor(): BaseViewModel() {

    var navigateToDoctorRegistration = SingleLiveEvent<Unit>()
    var navigateToPatientRegistration = SingleLiveEvent<Unit>()
    var navigateToClinicRegistration = SingleLiveEvent<Unit>()

    fun navigateToNextPage(selectedItemIndex: Int) {
        when(selectedItemIndex) {
            0 -> navigateToPatientRegistration.postValue(Unit)
            1 -> navigateToDoctorRegistration.postValue(Unit)
            2 -> navigateToClinicRegistration.postValue(Unit)
        }
    }
}