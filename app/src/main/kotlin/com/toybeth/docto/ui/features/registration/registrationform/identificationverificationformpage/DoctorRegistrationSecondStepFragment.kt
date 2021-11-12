package com.toybeth.docto.ui.features.registration.registrationform.identificationverificationformpage

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.toybeth.docto.R
import com.toybeth.docto.base.ui.BaseFragment
import com.toybeth.docto.base.utils.extensions.setContentView
import com.toybeth.docto.data.City
import com.toybeth.docto.data.State
import com.toybeth.docto.ui.features.registration.registrationform.DoctorRegistrationSecondScreen
import com.toybeth.docto.ui.features.registration.registrationform.RegistrationViewModel
import com.toybeth.docto.ui.theme.DoktoTheme
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
                DoktoTheme {
                    DoctorRegistrationSecondScreen(
                        viewModel,
                        this@DoctorRegistrationSecondStepFragment::showStateSelectionDialog,
                        this@DoctorRegistrationSecondStepFragment::showCitySelectionDialog,
                    )
                }
            }
        }

    override fun verifyStep(): VerificationError? {
        return if(viewModel.verifyDoctorRegistrationSecondStep()) {
            null
        } else {
            VerificationError("Fillup all fields")
        }
    }

    override fun onSelected() {

    }

    override fun onError(error: VerificationError) {

    }

    private fun showStateSelectionDialog() {
        viewModel.stateList.observe(viewLifecycleOwner, object: Observer<List<State>> {
            override fun onChanged(states: List<State>) {
                viewModel.stateList.removeObserver(this)
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(resources.getString(R.string.select_state))
                    .setItems(states.map { it.name }.toTypedArray()) { _, which ->
                        viewModel.setState(states[which])
                    }
                    .show()
            }
        })
    }

    private fun showCitySelectionDialog() {
        viewModel.cityList.observe(viewLifecycleOwner, object: Observer<List<City>> {
            override fun onChanged(cities: List<City>) {
                viewModel.cityList.removeObserver(this)
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(resources.getString(R.string.select_state))
                    .setItems(cities.map { it.name }.toTypedArray()) { _, which ->
                        viewModel.setCity(cities[which])
                    }
                    .show()
            }
        })
    }
}