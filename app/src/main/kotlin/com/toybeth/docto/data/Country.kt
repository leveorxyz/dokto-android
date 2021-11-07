package com.toybeth.docto.data

data class City(
	val name: String,
	val id: String,
	val stateId: String
)

data class State(
	val cities: List<City>
)

data class Country(
	val continent: String,
	val capital: String,
	val emojiU: String,
	val languages: List<String>,
	val emoji: String,
	val jsonMemberNative: String,
	val phone: String,
	val name: String,
	val currency: String,
	val states: List<State>
)

