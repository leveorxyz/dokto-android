package com.toybethsystems.dokto.paystack.data.model.response

import com.google.gson.annotations.SerializedName

data class PayStackSupportedBank(

	@SerializedName("country")
	val country: String,

	@SerializedName("code")
	val code: String,

	@SerializedName("pay_with_bank")
	val payWithBank: Boolean,

	@SerializedName("longcode")
	val longcode: String,

	@SerializedName("active")
	val active: Boolean,

	@SerializedName("type")
	val type: String,

	@SerializedName("createdAt")
	val createdAt: String,

	@SerializedName("is_deleted")
	val isDeleted: Boolean?,

	@SerializedName("name")
	val name: String,

	@SerializedName("currency")
	val currency: String,

	@SerializedName("id")
	val id: Int,

	@SerializedName("slug")
	val slug: String,

	@SerializedName("gateway")
	val gateway: String?,

	@SerializedName("updatedAt")
	val updatedAt: String
)
