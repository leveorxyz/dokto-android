package com.toybeth.docto.ui.features.registration.doctor.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.toybeth.docto.R
import com.toybeth.docto.base.ui.BaseViewBindingFragment
import com.toybeth.docto.databinding.FragmentRegistrationFormBinding
import com.toybeth.docto.ui.features.registration.patient.PatientRegistrationFormStepsFragmentDirections
import com.toybeth.dokto.stepper.StepperLayout
import com.toybeth.dokto.stepper.VerificationError
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@ExperimentalUnitApi
@AndroidEntryPoint
class DoctorRegistrationFormStepsFragment :
    BaseViewBindingFragment<RegistrationViewModel, FragmentRegistrationFormBinding>() {

    override val viewModel: RegistrationViewModel by viewModels()

    override val inflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> FragmentRegistrationFormBinding
        get() = FragmentRegistrationFormBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = getString(R.string.title_doctor_registration)
        val pageTitles =
            resources.getStringArray(R.array.doctor_registration_form_page_titles).toList()

        setKeyboardOpenListener()
        binding.registrationStepper.setAdapter(RegistrationFormStepsAdapter(pageTitles, this))
        binding.registrationStepper.setListener(object: StepperLayout.StepperListener {
            override fun onCompleted(completeButton: View?) {
                viewModel.registerDoctor()
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

        viewModel.registrationSuccess.observeOn(viewLifecycleOwner) {
            if (it == true) {
                Toast.makeText(
                    requireContext(),
                    "Registration Successful",
                    Toast.LENGTH_LONG
                ).show()
                navigateToRegistrationSuccessFragment()
            }
        }
    }

    private fun navigateToRegistrationSuccessFragment() {
        findNavController().navigate(
            DoctorRegistrationFormStepsFragmentDirections
                .actionDoctorRegistrationFormStepsFragmentToRegistrationCompleteFragment()
        )
    }

    private fun setKeyboardOpenListener() {
        setKeyboardOpenListener({
            binding.topView.visibility = View.GONE
        }, {
            binding.topView.visibility = View.VISIBLE
        })
    }
}