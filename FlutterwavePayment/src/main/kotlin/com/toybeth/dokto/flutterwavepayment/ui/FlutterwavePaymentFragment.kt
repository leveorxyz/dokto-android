package com.toybeth.dokto.flutterwavepayment.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.toybeth.docto.base.ui.BaseFragment
import com.toybeth.dokto.flutterwavepayment.R

class FlutterwavePaymentFragment() : BaseFragment<FlutterwavePaymentViewModel>() {

    override val viewModel: FlutterwavePaymentViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.fragment_flutterwave_payment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}