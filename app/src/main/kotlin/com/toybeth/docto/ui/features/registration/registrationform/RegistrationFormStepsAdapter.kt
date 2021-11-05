package com.toybeth.docto.ui.features.registration.registrationform

import androidx.fragment.app.Fragment
import com.toybeth.dokto.stepper.Step
import com.toybeth.dokto.stepper.adapter.AbstractFragmentStepAdapter
import com.toybeth.dokto.stepper.viewmodel.StepViewModel

class RegistrationFormStepsAdapter(fragment: Fragment) : AbstractFragmentStepAdapter(
    fragment.childFragmentManager,
    fragment.lifecycle,
    fragment.requireContext()
) {

    override fun getViewModel(position: Int): StepViewModel {
        val builder = StepViewModel.Builder(context)
            .setTitle("Tab title $position")
        if (position == 1) {
            builder.setSubtitle("Optional")
        }
        return builder
            .create()
    }

    override fun createStep(position: Int): Step {
        return RegistrationFirstStepFragment()
    }

    override fun getCount(): Int {
        return 5
    }
}