package com.toybeth.dokto.stripepayment.data.model

import com.google.gson.annotations.SerializedName

data class EphemeralKey(

	@SerializedName("associated_objects")
	val associatedObjects: List<AssociatedObjectsItem?>? = null,

	@SerializedName("expires")
	val expires: Int? = null,

	@SerializedName("livemode")
	val livemode: Boolean? = null,

	@SerializedName("created")
	val created: Int? = null,

	@SerializedName("id")
	val id: String? = null,

	@SerializedName("secret")
	val secret: String,

	@SerializedName("object")
	val `object`: String? = null
)

data class AssociatedObjectsItem(

	@SerializedName("id")
	val id: String? = null,

	@SerializedName("type")
	val type: String? = null
)
