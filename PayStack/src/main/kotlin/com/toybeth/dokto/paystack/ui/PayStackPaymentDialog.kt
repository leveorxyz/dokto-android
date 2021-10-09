package com.toybeth.dokto.paystack.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import co.paystack.android.Paystack
import co.paystack.android.PaystackSdk
import co.paystack.android.Transaction
import co.paystack.android.model.Charge
import com.orhanobut.logger.Logger
import com.toybeth.dokto.paystack.BuildConfig
import com.toybeth.dokto.paystack.R
import com.toybeth.dokto.paystack.databinding.DialogPaystackPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PayStackPaymentDialog : DialogFragment() {

    private lateinit var binding: DialogPaystackPaymentBinding
    private val viewModel: PayStackPaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.MyMaterialAlertDialogTheme90)
        initializePayStack()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogPaystackPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.payButton.setOnClickListener {
            if (getCardNumber() != "" && getCcv() != "" && getCardExpireDate() != "") {
                chargeFromCard(getCardNumber(), getCcv(), getCardExpireDate())
            }
        }

        viewModel.validCard.observeOn(viewLifecycleOwner) {
            val charge = Charge().apply {
                this.card = it
                this.amount = 50
                this.currency = "NGN"
                this.email = "shafayathossainkhan@gmail.com"
            }
            PaystackSdk.chargeCard(requireActivity(), charge, object: Paystack.TransactionCallback {
                override fun onSuccess(transaction: Transaction?) {
                    Logger.d("SUCCESS")
                    viewModel.validateTransaction(transaction)
                }

                override fun beforeValidate(transaction: Transaction?) {
                    Logger.d("BEFORE VALIDATE")

                }

                override fun onError(error: Throwable?, transaction: Transaction?) {
                    Logger.e(error, error?.message ?: "")
                }
            })
        }
    }

    private fun getCardNumber(): String {
        return binding.creditCardNumber.text.toString()
    }

    private fun getCcv(): String {
        return binding.creditCardCcv.text.toString()
    }

    private fun getCardExpireDate(): String {
        return binding.creditCardExpiry.text.toString()
    }

    private fun initializePayStack() {
        PaystackSdk.initialize(context)
        PaystackSdk.setPublicKey(BuildConfig.PAYSTACK_PUBLIC_KEY)
    }

    private fun chargeFromCard(cardNumber: String, ccv: String, expireDate: String) {
        viewModel.validateCard(cardNumber, ccv, expireDate)
    }
}