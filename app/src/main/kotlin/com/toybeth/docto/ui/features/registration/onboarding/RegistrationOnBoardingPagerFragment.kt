package com.toybeth.docto.ui.features.registration.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.toybeth.docto.R
import com.toybeth.docto.databinding.FragmentRegistrationOnboardingPageBinding

class RegistrationOnBoardingPagerFragment : Fragment() {

    companion object {
        const val CURRENT_PAGE_INDEX = "currentPage"
    }

    private lateinit var binding: FragmentRegistrationOnboardingPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationOnboardingPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makePageFullScreen()
        val currentPageIndex: Int = arguments?.getInt(CURRENT_PAGE_INDEX, 0) ?: 0
        loadPage(currentPageIndex)
    }

    private fun loadPage(pageIndexIndex: Int) {
        when (pageIndexIndex) {
            0 -> {
                binding.imgBackground.setImageResource(R.drawable.img_onboarding_background_1)
                binding.imgOnboarding.setImageResource(R.drawable.img_onboarding_1)
                binding.tvTitle.apply {
                    text = getString(R.string.onboarding_title_1)
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                }
                binding.tvDescription.text = getString(R.string.onboarding_text_1)
            }
            1 -> {
                binding.imgBackground.setImageResource(R.drawable.img_onboarding_background_2)
                binding.imgOnboarding.setImageResource(R.drawable.img_onboarding_2)
                binding.tvTitle.apply {
                    text = getString(R.string.onboarding_title_1)
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
                binding.tvDescription.text = getString(R.string.onboarding_text_1)
            }
            2 -> {
                binding.imgBackground.setImageResource(R.drawable.img_onboarding_background_3)
                binding.imgOnboarding.setImageResource(R.drawable.img_onboarding_3)
                binding.tvTitle.apply {
                    text = getString(R.string.onboarding_title_1)
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                }
                binding.tvDescription.text = getString(R.string.onboarding_text_1)
            }
        }
    }

    fun makePageFullScreen() {
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}