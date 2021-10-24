package com.toybeth.dokto.flutterwavepayment.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.flutterwave.raveandroid.RaveUiManager
import com.toybeth.docto.base.ui.BaseFragment
import com.toybeth.dokto.flutterwavepayment.R
import dagger.hilt.android.AndroidEntryPoint
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.flutterwave.raveandroid.RavePayActivity

import com.flutterwave.raveandroid.rave_java_commons.RaveConstants
import com.toybeth.docto.base.ui.BaseViewBindingFragment
import com.toybeth.dokto.flutterwavepayment.databinding.FragmentFlutterwavePaymentBinding

@AndroidEntryPoint
class FlutterwavePaymentFragment : BaseViewBindingFragment<FlutterwavePaymentViewModel, FragmentFlutterwavePaymentBinding>() {

    override val viewModel: FlutterwavePaymentViewModel by viewModels()
    private lateinit var raveUiManager: RaveUiManager

    override val inflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> FragmentFlutterwavePaymentBinding
        get() = FragmentFlutterwavePaymentBinding::inflate

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