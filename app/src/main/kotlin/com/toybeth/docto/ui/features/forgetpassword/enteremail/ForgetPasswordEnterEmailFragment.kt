package com.toybeth.docto.ui.features.forgetpassword.enteremail

import android.os.Bundle
import android.view.View
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.toybeth.docto.base.ui.BaseFragment
import com.toybeth.docto.base.utils.extensions.setContentView
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@ExperimentalUnitApi
@AndroidEntryPoint
class ForgetPasswordEnterEmailFragment : BaseFragment<ForgetPasswordEnterEmailViewModel>() {

    override val viewModel: ForgetPasswordEnterEmailViewModel by viewModels()

    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContentView {
                EnterEmailScreen(viewModel = viewModel)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.enterToScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigateToOtp.observeOn(viewLifecycleOwner) {
            if (it) {
                navigateToEnterOtpScreen()
            }
        }
    }

    private fun navigateToEnterOtpScreen() {
        findNavController().navigate(
            ForgetPasswordEnterEmailFragmentDirections.actionForgetPasswordEnterEmailFragmentToForgetPasswordEnterOtpFragment()
        )
    }
}