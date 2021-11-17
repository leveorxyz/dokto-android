package com.toybeth.docto.data

import com.google.gson.annotations.SerializedName

data class City(
	@SerializedName("name")
	val name: String,
)

data class State(
	@SerializedName("name")
	val name: String,
	@SerializedName("state_code")
	val code: String
)

data class Country(
	@SerializedName("name")
	val name: String,
	@SerializedName("country_code")
	val code: String,
	@SerializedName("phone_code")
	val phone: String
)