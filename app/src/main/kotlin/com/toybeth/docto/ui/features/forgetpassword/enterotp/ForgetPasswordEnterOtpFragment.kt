package com.toybeth.docto.ui.features.forgetpassword.enterotp

import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.viewModels
import com.toybeth.docto.base.ui.BaseFragment
import com.toybeth.docto.base.utils.extensions.setContentView
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalUnitApi
@AndroidEntryPoint
class ForgetPasswordEnterOtpFragment : BaseFragment<ForgetPasswordEnterOtpViewModel>() {

    override val viewModel: ForgetPasswordEnterOtpViewModel by viewModels()

    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContentView {
                EnterOtpScreen()
            }
        }
}