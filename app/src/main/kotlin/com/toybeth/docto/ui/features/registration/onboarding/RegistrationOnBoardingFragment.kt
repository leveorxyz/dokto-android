package com.toybeth.docto.ui.features.registration.onboarding

import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.viewModels
import com.google.accompanist.pager.ExperimentalPagerApi
import com.toybeth.docto.base.ui.BaseFragment
import com.toybeth.docto.base.utils.extensions.setContentView

class RegistrationOnBoardingFragment: BaseFragment<RegistrationOnBoardingViewModel>() {

    override val viewModel: RegistrationOnBoardingViewModel by viewModels()

    @ExperimentalPagerApi
    @ExperimentalUnitApi
    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContentView {
                RegistrationOnBoardingScreen()
            }
        }
}