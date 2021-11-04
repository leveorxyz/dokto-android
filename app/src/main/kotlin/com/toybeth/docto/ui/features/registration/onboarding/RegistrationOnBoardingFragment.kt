package com.toybeth.docto.ui.features.registration.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.viewModels
import com.google.accompanist.pager.ExperimentalPagerApi
import com.toybeth.docto.base.ui.BaseFragment
import com.toybeth.docto.base.ui.BaseViewBindingFragment
import com.toybeth.docto.base.utils.extensions.setContentView
import com.toybeth.docto.databinding.FragmentRegistrationOnboardingBinding

class RegistrationOnBoardingFragment: BaseViewBindingFragment<RegistrationOnBoardingViewModel, FragmentRegistrationOnboardingBinding>() {

    override val viewModel: RegistrationOnBoardingViewModel by viewModels()

    override val inflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> FragmentRegistrationOnboardingBinding
        get() = FragmentRegistrationOnboardingBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makePageFullScreen()
        binding.vpOnboarding.adapter = RegistrationOnBoardingPagerAdapter(this)
        binding.dotsIndicator.setViewPager2(binding.vpOnboarding)
    }
}