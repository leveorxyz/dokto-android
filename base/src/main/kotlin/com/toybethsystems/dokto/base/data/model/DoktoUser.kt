package com.toybethsystems.dokto.base.data.model

import com.google.gson.annotations.SerializedName

data class DoktoUser(

	@SerializedName("profile_photo")
	val profilePhoto: String = "",

	@SerializedName("id")
	val id: String = "",

	@SerializedName("email")
	val email: String = "",

	@SerializedName("token")
	val token: String = ""
)
