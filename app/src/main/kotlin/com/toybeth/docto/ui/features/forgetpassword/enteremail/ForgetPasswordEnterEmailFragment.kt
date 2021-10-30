package com.toybeth.docto.ui.features.forgetpassword.enteremail

import android.os.Bundle
import android.view.View
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.viewModels
import com.toybeth.docto.base.ui.BaseFragment
import com.toybeth.docto.base.utils.extensions.setContentView
import com.toybeth.docto.ui.features.forgetpassword.ForgetPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@ExperimentalUnitApi
@AndroidEntryPoint
class ForgetPasswordEnterEmailFragment : BaseFragment<ForgetPasswordEnterEmailViewModel>() {

    override val viewModel: ForgetPasswordEnterEmailViewModel by viewModels()

    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContentView {
                EnterEmailScreen(viewModel)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.enterToScreen()
    }
}