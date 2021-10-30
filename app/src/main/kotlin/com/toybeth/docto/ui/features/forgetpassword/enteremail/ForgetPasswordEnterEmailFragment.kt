package com.toybeth.docto.ui.features.forgetpassword.enteremail

import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.viewModels
import com.toybeth.docto.base.ui.BaseFragment
import com.toybeth.docto.base.utils.extensions.setContentView
import com.toybeth.docto.ui.features.forgetpassword.ForgetPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalUnitApi
@AndroidEntryPoint
class ForgetPasswordEnterEmailFragment : BaseFragment<ForgetPasswordViewModel>() {

    override val viewModel: ForgetPasswordViewModel by viewModels()

    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContentView {
                EnterEmailScreen()
            }
        }
}