package com.toybethsystems.dokto.ui.features.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class RegistrationOnBoardingPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putInt(RegistrationOnBoardingPagerFragment.CURRENT_PAGE_INDEX, position)
        val fragment = RegistrationOnBoardingPagerFragment()
        fragment.arguments = bundle
        return fragment
    }
}