package com.toybethsystems.dokto.ui.features.registration.doctor.form.second

import android.os.Bundle
import android.view.View
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.toybethsystems.dokto.R
import com.toybethsystems.dokto.base.ui.BaseFragment
import com.toybethsystems.dokto.base.utils.extensions.setContentView
import com.toybethsystems.dokto.data.City
import com.toybethsystems.dokto.data.State
import com.toybethsystems.dokto.ui.features.registration.doctor.form.RegistrationViewModel
import com.toybeth.dokto.stepper.Step
import com.toybeth.dokto.stepper.VerificationError

@ExperimentalMaterialApi
@ExperimentalUnitApi
class DoctorRegistrationSecondStepFragment : BaseFragment<RegistrationViewModel>(), Step {

    override val viewModel: RegistrationViewModel by viewModels(
        ownerProducer = { requireParentFragment() },
    )

    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContentView {
                DoctorRegistrationSecondScreen(
                    viewModel,
                    this@DoctorRegistrationSecondStepFragment::showStateSelectionDialog,
                    this@DoctorRegistrationSecondStepFragment::showCitySelectionDialog,
                )
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun verifyStep(): VerificationError? {
        return if(viewModel.verifyDoctorRegistrationSecondStep()) {
            null
        } else {
            VerificationError("Fill-up all fields")
        }
    }

    override fun onSelected() {

    }

    override fun onError(error: VerificationError) {

    }

    private fun showStateSelectionDialog() {
        viewModel.stateList.observe(viewLifecycleOwner, object : Observer<List<State>> {
            override fun onChanged(states: List<State>) {
                viewModel.stateList.removeObserver(this)
                MaterialAlertDialogBuilder(
                    requireContext(),
                    R.style.MaterialAlertDialog_Rounded
                )
                    .setTitle(resources.getString(R.string.select_state))
                    .setItems(states.map { it.name }.toTypedArray()) { _, which ->
                        viewModel.setState(states[which])
                        viewModel.selectedStateName.error.value = null
                    }
                    .show()
            }
        })
    }

    private fun showCitySelectionDialog() {
        viewModel.cityList.observe(viewLifecycleOwner, object : Observer<List<City>> {
            override fun onChanged(cities: List<City>) {
                viewModel.cityList.removeObserver(this)
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(resources.getString(R.string.select_state))
                    .setItems(cities.map { it.name }.toTypedArray()) { _, which ->
                        viewModel.setCity(cities[which])
                        viewModel.selectedCityName.error.value = null
                    }
                    .show()
            }
        })
    }
}