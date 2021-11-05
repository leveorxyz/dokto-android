package com.toybeth.docto.ui.features.registration.registrationform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.toybeth.docto.base.ui.BaseViewBindingFragment
import com.toybeth.docto.databinding.FragmentRegistrationFormBinding

class RegistrationFormStepsFragment : BaseViewBindingFragment<RegistrationViewModel, FragmentRegistrationFormBinding>() {

    override val viewModel: RegistrationViewModel by viewModels()

    override val inflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> FragmentRegistrationFormBinding
        get() = FragmentRegistrationFormBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registrationStepper.setAdapter(RegistrationFormStepsAdapter(this))
    }
}