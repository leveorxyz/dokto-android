package com.toybeth.docto.ui.features.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.toybeth.docto.base.ui.BaseViewBindingFragment
import com.toybeth.docto.databinding.FragmentRegistrationOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationOnBoardingFragment: BaseViewBindingFragment<RegistrationOnBoardingViewModel, FragmentRegistrationOnboardingBinding>() {

    override val viewModel: RegistrationOnBoardingViewModel by viewModels()

    override val inflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> FragmentRegistrationOnboardingBinding
        get() = FragmentRegistrationOnboardingBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makePageFullScreen()

        binding.vpOnboarding.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(position == 2) {
                    binding.btnGetStarted.visibility = View.VISIBLE
                } else {
                    binding.btnGetStarted.visibility = View.GONE
                }
            }
        })

        binding.vpOnboarding.adapter = RegistrationOnBoardingPagerAdapter(this)
        binding.dotsIndicator.setViewPager2(binding.vpOnboarding)

        binding.btnSkip.setOnClickListener {
            navigateToLogin()
        }

        binding.btnGetStarted.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        viewModel.onBoardingPassed()
        findNavController().navigate(
            RegistrationOnBoardingFragmentDirections.actionRegistrationOnBoardingFragmentToLoginFragment()
        )
    }
}