package com.toybeth.dokto.paypalpayment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.toybeth.docto.base.ui.BaseViewBindingFragment
import com.toybeth.dokto.paypalpayment.databinding.FragmentPaypalPaymentBinding
import com.toybeth.dokto.paypalpayment.extensions.setupData

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