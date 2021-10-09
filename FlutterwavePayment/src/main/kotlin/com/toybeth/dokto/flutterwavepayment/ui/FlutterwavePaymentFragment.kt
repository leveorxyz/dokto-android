package com.toybeth.dokto.flutterwavepayment.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.flutterwave.raveandroid.RaveUiManager
import com.toybeth.docto.base.ui.BaseFragment
import com.toybeth.dokto.flutterwavepayment.R
import dagger.hilt.android.AndroidEntryPoint
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.flutterwave.raveandroid.RavePayActivity

import com.flutterwave.raveandroid.rave_java_commons.RaveConstants

@AndroidEntryPoint
class FlutterwavePaymentFragment : BaseFragment<FlutterwavePaymentViewModel>() {

    override val viewModel: FlutterwavePaymentViewModel by viewModels()
    private lateinit var raveUiManager: RaveUiManager

    override val layoutId: Int
        get() = R.layout.fragment_flutterwave_payment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        raveUiManager = RaveUiManager(this)
            .withTheme(R.style.FlutterwaveTheme)
            .apply {
                viewModel.configureRaveUiManager(this)
            }

        raveUiManager.initialize()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            val message = data.getStringExtra("response")
            when (resultCode) {
                RavePayActivity.RESULT_SUCCESS -> {
                    Toast.makeText(context, "SUCCESS $message", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                RavePayActivity.RESULT_ERROR -> {
                    Toast.makeText(context, "ERROR $message", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                RavePayActivity.RESULT_CANCELLED -> {
                    Toast.makeText(context, "CANCELLED $message", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }
    }
}