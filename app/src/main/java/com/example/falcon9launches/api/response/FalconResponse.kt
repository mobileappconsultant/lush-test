package com.example.falcon9launches.api.response

import com.google.gson.annotations.SerializedName

data class FalconResponse(
	@SerializedName("links")
	val links: Links? = null,
	@SerializedName("id")
	val id: String? = null,
	@SerializedName("date_utc")
	val dateUtc: String? = null,
	@SerializedName("success")
	val success: Boolean? = null,
	@SerializedName("name")
	val name: String? = null,

)

data class Patch(
	@SerializedName("small")
	val small: String? = null,
	@SerializedName("large")
	val large: String? = null
)

data class Links(
	@SerializedName("patch")
	val patch: Patch? = null,
)

