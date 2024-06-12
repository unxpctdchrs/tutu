package com.tuners.tutu.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserDetailsResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("userDetails")
	val userDetails: List<UserDetailsItem>
)

data class UserDetailsItem(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("address")
	val address: Any,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String,

	@field:SerializedName("balance")
	val balance: Int,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("birthDatePlace")
	val birthDatePlace: Any,

	@field:SerializedName("jenjangPendidikan")
	val jenjangPendidikan: Any,

	@field:SerializedName("isMentor")
	val isMentor: Boolean,

	@field:SerializedName("uuid")
	val uuid: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("rating")
	val rating: Float,
)
