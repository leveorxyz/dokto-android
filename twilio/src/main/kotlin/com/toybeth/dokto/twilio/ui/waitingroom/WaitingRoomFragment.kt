package com.toybeth.dokto.twilio.ui.waitingroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.toybeth.docto.base.ui.BaseViewBindingFragment
import com.toybeth.dokto.twilio.databinding.FragmentWaitingRoomBinding

class WaitingRoomFragment : BaseViewBindingFragment<WaitingRoomViewModel, FragmentWaitingRoomBinding>() {

    override val viewModel: WaitingRoomViewModel by viewModels()

    override val inflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> FragmentWaitingRoomBinding
        get() = FragmentWaitingRoomBinding::inflate


}