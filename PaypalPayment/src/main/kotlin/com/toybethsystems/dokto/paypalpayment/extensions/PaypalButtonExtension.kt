package com.toybethsystems.dokto.paypalpayment.extensions

import com.orhanobut.logger.Logger
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.cancel.OnCancel
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.error.OnError
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.Order
import com.paypal.checkout.order.PurchaseUnit
import com.paypal.checkout.paymentbutton.PayPalButton

fun PayPalButton.setupData(
    amount: String,
    onSuccess: (() -> Unit)? = null,
    onError: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null
) {
    setup(
        createOrder = CreateOrder { createOrderActions ->
            val order = Order(
                intent = OrderIntent.CAPTURE,
                appContext = AppContext(
                    userAction = UserAction.PAY_NOW
                ),
                purchaseUnitList = listOf(
                    PurchaseUnit(
                        amount = Amount(
                            currencyCode = CurrencyCode.USD,
                            value = amount
                        )
                    )
                )
            )

            createOrderActions.create(order)
        },
        onApprove = OnApprove { approval ->
            approval.orderActions.capture { captureOrderResult ->
                Logger.d("CaptureOrderResult: $captureOrderResult")
                onSuccess?.invoke()
            }
        },
        onCancel = OnCancel {
            Logger.d("Buyer canceled the PayPal experience.")
            onCancel?.invoke()
        },
        onError = OnError { errorInfo ->
            Logger.e("Error: $errorInfo")
            onError?.invoke()
        }
    )
}