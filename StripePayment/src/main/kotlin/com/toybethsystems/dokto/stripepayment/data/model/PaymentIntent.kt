package com.toybethsystems.dokto.stripepayment.data.model

import com.google.gson.annotations.SerializedName

data class PaymentIntent(

	@SerializedName("metadata")
	val metadata: Metadata? = null,

	@SerializedName("livemode")
	val livemode: Boolean? = null,

	@SerializedName("canceled_at")
	val canceledAt: Any? = null,

	@SerializedName("amount_capturable")
	val amountCapturable: Int? = null,

	@SerializedName("description")
	val description: Any? = null,

	@SerializedName("source")
	val source: Any? = null,

	@SerializedName("statement_descriptor")
	val statementDescriptor: Any? = null,

	@SerializedName("transfer_data")
	val transferData: Any? = null,

	@SerializedName("shipping")
	val shipping: Any? = null,

	@SerializedName("review")
	val review: Any? = null,

	@SerializedName("currency")
	val currency: String? = null,

	@SerializedName("id")
	val id: String? = null,

	@SerializedName("client_secret")
	val clientSecret: String,

	@SerializedName("payment_method_options")
	val paymentMethodOptions: PaymentMethodOptions? = null,

	@SerializedName("payment_method")
	val paymentMethod: Any? = null,

	@SerializedName("capture_method")
	val captureMethod: String? = null,

	@SerializedName("amount")
	val amount: Int? = null,

	@SerializedName("transfer_group")
	val transferGroup: Any? = null,

	@SerializedName("on_behalf_of")
	val onBehalfOf: Any? = null,

	@SerializedName("created")
	val created: Int? = null,

	@SerializedName("payment_method_types")
	val paymentMethodTypes: List<String?>? = null,

	@SerializedName("amount_received")
	val amountReceived: Int? = null,

	@SerializedName("setup_future_usage")
	val setupFutureUsage: Any? = null,

	@SerializedName("confirmation_method")
	val confirmationMethod: String? = null,

	@SerializedName("cancellation_reason")
	val cancellationReason: Any? = null,

	@SerializedName("charges")
	val charges: Charges? = null,

	@SerializedName("application")
	val application: Any? = null,

	@SerializedName("receipt_email")
	val receiptEmail: Any? = null,

	@SerializedName("last_payment_error")
	val lastPaymentError: Any? = null,

	@SerializedName("next_action")
	val nextAction: Any? = null,

	@SerializedName("invoice")
	val invoice: Any? = null,

	@SerializedName("statement_descriptor_suffix")
	val statementDescriptorSuffix: Any? = null,

	@SerializedName("application_fee_amount")
	val applicationFeeAmount: Any? = null,

	@SerializedName("object")
	val `object`: String? = null,

	@SerializedName("customer")
	val customer: String? = null,

	@SerializedName("status")
	val status: String? = null
)

data class Charges(

	@SerializedName("data")
	val data: List<Any?>? = null,

	@SerializedName("total_count")
	val totalCount: Int? = null,

	@SerializedName("has_more")
	val hasMore: Boolean? = null,

	@SerializedName("url")
	val url: String? = null,

	@SerializedName("object")
	val `object`: String? = null
)

data class Card(

	@SerializedName("installments")
	val installments: Any? = null,

	@SerializedName("request_three_d_secure")
	val requestThreeDSecure: String? = null,

	@SerializedName("network")
	val network: Any? = null
)

data class Metadata(
	val any: Any? = null
)

data class PaymentMethodOptions(

	@SerializedName("card")
	val card: Card? = null
)
