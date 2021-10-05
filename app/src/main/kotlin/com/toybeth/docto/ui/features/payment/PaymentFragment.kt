package com.toybeth.docto.ui.features.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.toybeth.docto.R

class PaymentFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_payment,
            container,
            false
        ).apply {
            findViewById<ComposeView>(R.id.payment_screen).setContent {
                PaymentScreen()
            }
        }
    }
}