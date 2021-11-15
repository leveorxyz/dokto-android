package com.toybeth.docto.data

import com.google.gson.annotations.SerializedName

data class City(
	@SerializedName("id")
	val id: Int,
	@SerializedName("name")
	val name: String,
)

data class State(
	@SerializedName("id")
	val id: Int,
	@SerializedName("name")
	val name: String,
	@SerializedName("cities")
	val cities: List<City>
)

data class Country(
	@SerializedName("id")
	val id: Int,
	@SerializedName("name")
	val name: String,
	@SerializedName("phone_code")
	val phone: String,
	@SerializedName("region")
	val region: String,
	@SerializedName("states")
	val states: List<State>
)

