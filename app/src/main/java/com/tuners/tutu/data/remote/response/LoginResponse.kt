package com.tuners.tutu.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("loginResult")
	val loginResult: List<LoginResultItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class LoginResultItem(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("address")
	val address: Any,

	@field:SerializedName("balance")
	val balance: Int,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("isMentor")
	val isMentor: Boolean,

	@field:SerializedName("uuid")
	val uuid: String,

	@field:SerializedName("username")
	val username: String
)
