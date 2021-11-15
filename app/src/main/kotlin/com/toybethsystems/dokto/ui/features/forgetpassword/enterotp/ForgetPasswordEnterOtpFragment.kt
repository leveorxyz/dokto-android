package com.toybethsystems.dokto.ui.features.forgetpassword.enterotp

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.viewModels
import com.toybethsystems.dokto.base.ui.BaseFragment
import com.toybethsystems.dokto.base.utils.extensions.setContentView
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@ExperimentalUnitApi
@AndroidEntryPoint
class ForgetPasswordEnterOtpFragment : BaseFragment<ForgetPasswordEnterOtpViewModel>() {

    override val viewModel: ForgetPasswordEnterOtpViewModel by viewModels()

    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContentView {
                EnterOtpScreen(viewModel)
            }
        }
}