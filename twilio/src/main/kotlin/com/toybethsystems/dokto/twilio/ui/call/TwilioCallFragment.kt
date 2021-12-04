package com.toybethsystems.dokto.twilio.ui.call

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.orhanobut.logger.Logger
import com.toybethsystems.dokto.base.ui.BaseViewBindingFragment
import com.toybethsystems.dokto.twilio.databinding.FragmentTwilioCallBinding
import com.toybethsystems.dokto.twilio.ui.common.TwilioCallViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class TwilioCallFragment : BaseViewBindingFragment<TwilioCallViewModel, FragmentTwilioCallBinding>() {

    override val viewModel: TwilioCallViewModel by viewModels()

    override val inflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> FragmentTwilioCallBinding
        get() = FragmentTwilioCallBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.twilioCallScreen.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                TwilioCallScreen(::endCall)
            }
        }

        if(viewModel.checkPermissionForCameraAndMicrophone()) {
            viewModel.initializeTwilio()
        }
    }

    override fun onPause() {
        Logger.d("STOP")
        viewModel.pause()
        super.onPause()
    }

    private fun endCall() {
        viewModel.endCall()
        findNavController().popBackStack()
    }
}