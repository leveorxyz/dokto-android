package com.toybeth.dokto.twilio.ui.waitingroom

import com.toybeth.docto.base.ui.BaseViewModel
import com.toybeth.dokto.twilio.data.TwilioCallRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WaitingRoomViewModel @Inject constructor(
    private val repository: TwilioCallRepository
) : BaseViewModel() {

    fun connectToWaitingRoom() {

    }
}