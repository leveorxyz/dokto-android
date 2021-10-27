package com.toybethsystems.dokto.twilio.ui.waitingroom

import com.toybethsystems.dokto.base.ui.BaseViewModel
import com.toybethsystems.dokto.twilio.data.TwilioCallRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WaitingRoomViewModel @Inject constructor(
    private val repository: TwilioCallRepository
) : BaseViewModel() {

    fun connectToWaitingRoom() {

    }
}