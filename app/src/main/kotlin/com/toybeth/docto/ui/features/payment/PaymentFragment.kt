package com.toybeth.docto.ui.features.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.toybeth.docto.R
import com.toybeth.docto.base.ui.BaseFragment
import com.toybeth.docto.base.ui.BaseViewBindingFragment
import com.toybeth.docto.databinding.FragmentPaymentBinding
import com.toybeth.dokto.paypalpayment.setupData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : BaseViewBindingFragment<PaymentViewModel, FragmentPaymentBinding>() {

    override val viewModel: PaymentViewModel by viewModels()

    override val inflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> FragmentPaymentBinding
        get() = FragmentPaymentBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPaypal.setupData("50.0")
    }
}