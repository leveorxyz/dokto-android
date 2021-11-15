package com.toybethsystems.dokto.ui.features.registration.clinic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.viewModels
import com.toybethsystems.dokto.R
import com.toybethsystems.dokto.base.ui.BaseViewBindingFragment
import com.toybethsystems.dokto.databinding.FragmentRegistrationFormBinding
import com.toybethsystems.dokto.ui.features.registration.doctor.form.RegistrationFormStepsAdapter
import com.toybethsystems.dokto.ui.features.registration.patient.PatientRegistrationViewModel
import com.toybeth.dokto.stepper.StepperLayout
import com.toybeth.dokto.stepper.VerificationError
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalUnitApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class ClinicRegistrationFormStepsFragment :
    BaseViewBindingFragment<ClinicRegistrationViewModel, FragmentRegistrationFormBinding>()  {

    override val viewModel: ClinicRegistrationViewModel by viewModels()

    override val inflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> FragmentRegistrationFormBinding
        get() = FragmentRegistrationFormBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pageTitles =
            resources.getStringArray(R.array.clinic_registration_form_page_titles).toList()
        binding.registrationStepper.setAdapter(ClinicRegistrationFormStepsAdapter(pageTitles, this))
        binding.registrationStepper.setListener(object: StepperLayout.StepperListener {
            override fun onCompleted(completeButton: View?) {
                viewModel.registerClinic()
            }

            override fun onError(verificationError: VerificationError?) {
//                TODO("Not yet implemented")
            }

            override fun onStepSelected(newStepPosition: Int) {
//                TODO("Not yet implemented")
            }

            override fun onReturn() {
//                TODO("Not yet implemented")
            }
        })

        viewModel.moveNext.observeOn(viewLifecycleOwner) {
            binding.registrationStepper.proceed()
        }
    }
}