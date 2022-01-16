package com.toybethsystems.dokto.stripepayment.data.model

import com.google.gson.annotations.SerializedName

data class StripeCustomer(

	@SerializedName("metadata")
	val metadata: ResponseMetadata? = null,

	@SerializedName("subscriptions")
	val subscriptions: Subscriptions? = null,

	@SerializedName("account_balance")
	val accountBalance: Int? = null,

	@SerializedName("livemode")
	val livemode: Boolean? = null,

	@SerializedName("sources")
	val sources: Sources? = null,

	@SerializedName("tax_ids")
	val taxIds: TaxIds? = null,

	@SerializedName("next_invoice_sequence")
	val nextInvoiceSequence: Int? = null,

	@SerializedName("description")
	val description: Any? = null,

	@SerializedName("discount")
	val discount: Any? = null,

	@SerializedName("tax_info_verification")
	val taxInfoVerification: Any? = null,

	@SerializedName("preferred_locales")
	val preferredLocales: List<Any?>? = null,

	@SerializedName("balance")
	val balance: Int? = null,

	@SerializedName("shipping")
	val shipping: Any? = null,

	@SerializedName("delinquent")
	val delinquent: Boolean? = null,

	@SerializedName("currency")
	val currency: Any? = null,

	@SerializedName("id")
	val id: String,

	@SerializedName("email")
	val email: Any? = null,

	@SerializedName("invoice_settings")
	val invoiceSettings: InvoiceSettings? = null,

	@SerializedName("address")
	val address: Any? = null,

	@SerializedName("default_source")
	val defaultSource: Any? = null,

	@SerializedName("invoice_prefix")
	val invoicePrefix: String? = null,

	@SerializedName("tax_exempt")
	val taxExempt: String? = null,

	@SerializedName("created")
	val created: Int? = null,

	@SerializedName("tax_info")
	val taxInfo: Any? = null,

	@SerializedName("phone")
	val phone: Any? = null,

	@SerializedName("name")
	val name: Any? = null,

	@SerializedName("object")
	val `object`: String? = null
)

data class Subscriptions(

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

data class TaxIds(

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

data class ResponseMetadata(
	val any: Any? = null
)

data class InvoiceSettings(

	@SerializedName("footer")
	val footer: Any? = null,

	@SerializedName("custom_fields")
	val customFields: Any? = null,

	@SerializedName("default_payment_method")
	val defaultPaymentMethod: Any? = null
)

data class Sources(

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
