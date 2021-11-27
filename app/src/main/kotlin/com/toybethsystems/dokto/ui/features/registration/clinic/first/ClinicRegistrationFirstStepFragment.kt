package com.toybethsystems.dokto.ui.features.registration.clinic.first

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.toybethsystems.dokto.R
import com.toybethsystems.dokto.base.ui.BaseFragment
import com.toybethsystems.dokto.base.utils.extensions.setContentView
import com.toybethsystems.dokto.data.Country
import com.toybethsystems.dokto.ui.features.registration.clinic.ClinicRegistrationViewModel
import com.toybethsystems.dokto.ui.features.registration.doctor.form.RegistrationViewModel
import com.toybethsystems.dokto.ui.features.registration.doctor.form.first.DoctorRegistrationFirstScreen
import com.toybeth.dokto.stepper.Step
import com.toybeth.dokto.stepper.VerificationError
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ClinicRegistrationFirstStepFragment : BaseFragment<ClinicRegistrationViewModel>(), Step {

    private var datePicker: MaterialDatePicker<Long>? = null

    override val viewModel: ClinicRegistrationViewModel by viewModels(
        ownerProducer = { requireParentFragment() },
    )

    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContentView {
                ClinicRegistrationFirstScreen(
                    viewModel,
                    this@ClinicRegistrationFirstStepFragment::showCountrySelectionDialog,
                )
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun verifyStep(): VerificationError? {
        return if (viewModel.verifyClinicRegistrationFirstStep()) {
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

    private fun showCountrySelectionDialog() {
        viewModel.countryList.observe(viewLifecycleOwner, object : Observer<List<Country>> {
            override fun onChanged(countries: List<Country>) {
                viewModel.countryList.removeObserver(this)
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(resources.getString(R.string.select_state))
                    .setItems(countries.map { "${it.name} (${it.phone})" }
                        .toTypedArray()) { _, which ->
                        viewModel.setCountry(countries[which])
                    }
                    .show()
            }
        })
    }
}