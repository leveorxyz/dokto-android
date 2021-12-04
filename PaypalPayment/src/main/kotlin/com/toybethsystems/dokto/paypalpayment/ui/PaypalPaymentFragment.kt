package com.toybethsystems.dokto.paypalpayment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.toybethsystems.dokto.base.ui.BaseViewBindingFragment
import com.toybethsystems.dokto.paypalpayment.databinding.FragmentPaypalPaymentBinding
import com.toybethsystems.dokto.paypalpayment.extensions.setupData

class PaypalPaymentFragment :
    BaseViewBindingFragment<PaypalPaymentViewModel, FragmentPaypalPaymentBinding>() {

    override val viewModel: PaypalPaymentViewModel by viewModels()

    override val inflater: (
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ) -> FragmentPaypalPaymentBinding
        get() = FragmentPaypalPaymentBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnPaypal.setupData(
                amount = "100"
            )
        }
    }
}