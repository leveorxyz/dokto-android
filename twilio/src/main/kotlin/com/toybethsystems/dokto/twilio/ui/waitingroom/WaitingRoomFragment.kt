package com.toybethsystems.dokto.twilio.ui.waitingroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.toybethsystems.dokto.twilio.databinding.FragmentWaitingRoomBinding
import com.toybethsystems.dokto.base.ui.BaseViewBindingFragment

class WaitingRoomFragment : BaseViewBindingFragment<WaitingRoomViewModel, FragmentWaitingRoomBinding>() {

    override val viewModel: WaitingRoomViewModel by viewModels()

    override val inflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> FragmentWaitingRoomBinding
        get() = FragmentWaitingRoomBinding::inflate


}