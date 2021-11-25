package com.toybeth.docto.ui.features.registration.patient.second

import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.toybeth.docto.R
import com.toybeth.docto.base.ui.BaseFragment
import com.toybeth.docto.base.utils.extensions.setContentView
import com.toybeth.docto.data.City
import com.toybeth.docto.data.State
import com.toybeth.docto.ui.features.registration.patient.PatientRegistrationViewModel
import com.toybeth.dokto.stepper.Step
import com.toybeth.dokto.stepper.VerificationError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientRegistrationSecondStepFragment : BaseFragment<PatientRegistrationViewModel>(), Step {

    override val viewModel: PatientRegistrationViewModel by viewModels(
        ownerProducer = { requireParentFragment() },
    )

    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContentView {
                PatientRegistrationSecondScreen(
                    viewModel,
                    this@PatientRegistrationSecondStepFragment::showStateSelectionDialog,
                    this@PatientRegistrationSecondStepFragment::showCitySelectionDialog,
                )
            }
        }

    override fun verifyStep(): VerificationError? {
        return if(viewModel.verifySecondPage()) {
            null
        } else {
            VerificationError("Fill-up all fields")
        }
    }

    override fun onSelected() {

    }

    override fun onError(error: VerificationError) {
//        TODO("Not yet implemented")
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
                        viewModel.stateName.error.value = null
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
                        viewModel.cityName.error.value = null
                    }
                    .show()
            }
        })
    }
}