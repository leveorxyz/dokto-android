package com.toybethsystems.dokto.ui.features.registration.patient.first

import android.os.Bundle
import android.view.View
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
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
import com.toybethsystems.dokto.ui.features.registration.patient.PatientRegistrationViewModel
import com.toybeth.dokto.stepper.Step
import com.toybeth.dokto.stepper.VerificationError
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@ExperimentalUnitApi
@AndroidEntryPoint
@ExperimentalMaterialApi
class PatientRegistrationFirstStepFragment : BaseFragment<PatientRegistrationViewModel>(), Step {

    private var datePicker: MaterialDatePicker<Long>? = null

    override val viewModel: PatientRegistrationViewModel by viewModels(
        ownerProducer = { requireParentFragment() },
    )

    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContentView {
                PatientRegistrationFirstScreen(
                    viewModel,
                    this@PatientRegistrationFirstStepFragment::showDatePicker,
                    this@PatientRegistrationFirstStepFragment::showCountrySelectionDialog,
                )
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun verifyStep(): VerificationError? {
        return if (viewModel.verifyFirstPage()) {
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
                    .setTitle(resources.getString(R.string.select_country))
                    .setItems(countries.map { "${it.name} (${it.phone})" }
                        .toTypedArray()) { _, which ->
                        viewModel.setCountry(countries[which])
                        viewModel.country.error.value = null
                    }
                    .show()
            }
        })
    }

    private fun showDatePicker() {
        try {
            datePicker?.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val startDate = Calendar.getInstance()
        startDate.set(Calendar.YEAR, 1950)
        val endDate = Calendar.getInstance()
        val constraints = CalendarConstraints.Builder()
            .setStart(startDate.timeInMillis)
            .setEnd(Calendar.getInstance().timeInMillis)
            .setValidator(DateValidatorPointBackward.before(endDate.timeInMillis))
            .build()
        datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.select_date_of_birth))
            .setCalendarConstraints(constraints)
            .build()
        datePicker?.addOnPositiveButtonClickListener {
            viewModel.setDateOfBirth(it)
            viewModel.dateOfBirth.error.value = null
        }
        datePicker?.show(childFragmentManager.beginTransaction(), "date picker")
    }
}