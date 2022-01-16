package com.toybethsystems.dokto.flutterwavepayment.ui

import com.flutterwave.raveandroid.RaveUiManager
import com.toybethsystems.dokto.base.ui.BaseViewModel
import com.toybethsystems.dokto.flutterwavepayment.BuildConfig
import com.toybethsystems.dokto.flutterwavepayment.data.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FlutterwavePaymentViewModel @Inject constructor() : BaseViewModel() {

    fun configureRaveUiManager(raveUiManager: RaveUiManager) {
        raveUiManager.apply {
            amount = 100.0
            txRef = "MC-${System.currentTimeMillis()}"
            publicKey = BuildConfig.FLUTTERWAVE_PUBLIC_KEY
            encryptionKey = BuildConfig.FLUTTERWAVE_ENCRYPTION_KEY

            // TODO: Set Country Code
            country = Country.Nigeria.code

            acceptAccountPayments(true)
            acceptCardPayments(true)
            
            when(country) {
                Country.Nigeria.code -> {
                    currency = Country.Nigeria.currency
                    acceptBankTransferPayments(true)
                    acceptUssdPayments(true)
                }
                Country.Kenya.code -> {
                    currency = Country.Kenya.currency
                    acceptMpesaPayments(true)
                }
                Country.Ghana.code -> {
                    currency = Country.Ghana.currency
                    acceptGHMobileMoneyPayments(true)
                }
                Country.Uganda.code -> {
                    currency = Country.Uganda.currency
                    acceptUgMobileMoneyPayments(true)
                }
                Country.Rwanda.code -> {
                    currency = Country.Rwanda.currency
                    acceptRwfMobileMoneyPayments(true)
                }
                Country.Zambia.code -> {
                    currency = Country.Zambia.currency
                    acceptZmMobileMoneyPayments(true)
                }
                Country.SouthAfrica.code -> {
                    currency = Country.SouthAfrica.currency
                    acceptSaBankPayments(true)
                }
                Country.UnitedKingdom.code -> {
                    currency = Country.UnitedKingdom.currency
                    acceptUkPayments(true)
                }
                Country.UnitedStates.code -> {
                    currency = Country.UnitedStates.currency
                    acceptAchPayments(true)
                    acceptAccountPayments(true)
                }
                else -> {
                    acceptFrancMobileMoneyPayments(true, country)
                }
            }

            acceptBarterPayments(true)
            allowSaveCardFeature(true)
            shouldDisplayFee(true)
            showStagingLabel(false)
            isPreAuth(false)
        }
    }
}