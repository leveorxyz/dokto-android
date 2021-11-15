package com.toybethsystems.dokto.ui.features.registration.usertype

import android.os.Bundle
import android.view.View
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.toybethsystems.dokto.base.ui.BaseFragment
import com.toybethsystems.dokto.base.utils.extensions.setContentView
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@ExperimentalUnitApi
@AndroidEntryPoint
class SelectRegistrationUserTypeFragment : BaseFragment<SelectRegistrationUserTypeViewModel>() {

    override val viewModel: SelectRegistrationUserTypeViewModel by viewModels()

    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContentView {
                SelectRegistrationUserTypeScreen(viewModel)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigateToDoctorRegistration.observeOn(viewLifecycleOwner) {
            navigateToDoctorRegistration()
        }

        viewModel.navigateToPatientRegistration.observeOn(viewLifecycleOwner) {
            navigateToPatientRegistration()
        }

        viewModel.navigateToClinicRegistration.observeOn(viewLifecycleOwner) {
            navigateToClinicRegistration()
        }
    }

    private fun navigateToDoctorRegistration() {
        findNavController().navigate(
            SelectRegistrationUserTypeFragmentDirections.actionSelectRegistrationUserTypeFragmentToDoctorRegistrationFormStepsFragment()
        )
    }

    private fun navigateToPatientRegistration() {
        findNavController().navigate(
            SelectRegistrationUserTypeFragmentDirections.actionSelectRegistrationUserTypeFragmentToPatientRegistrationFormStepsFragment()
        )
    }

    private fun navigateToClinicRegistration() {
//        findNavController().navigate(
//            SelectRegistrationUserTypeFragmentDirections.actionSelectRegistrationUserTypeFragmentToClinicRegistrationFormStepsFragment()
//        )
    }
}