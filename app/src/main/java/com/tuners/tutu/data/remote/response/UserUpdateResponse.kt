package com.tuners.tutu.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserUpdateResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
