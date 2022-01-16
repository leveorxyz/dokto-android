package com.toybethsystems.dokto.paystack.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.orhanobut.logger.Logger
import com.toybethsystems.dokto.paystack.R
import com.toybethsystems.dokto.paystack.databinding.DialogPaystackPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PayStackPaymentDialog : DialogFragment() {

    private val viewModel: PayStackPaymentViewModel by viewModels()
    private lateinit var binding: DialogPaystackPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.MyMaterialAlertDialogTheme90)
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

        binding.paymentWebView.apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
            }
            webChromeClient = WebChromeClient()
            webViewClient = object: WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    Logger.d("PAGE STARTED")
                }
            }
        }

        viewModel.paymentUrl.observeOn(viewLifecycleOwner) {
            Logger.d(it)
            binding.paymentWebView.loadUrl(it)
        }

        viewModel.initializeTransaction("shafayathossainkhan@gmail.com", "2000")
    }
}