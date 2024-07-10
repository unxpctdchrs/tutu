package com.tuners.tutu.data.remote.response

import com.google.gson.annotations.SerializedName

data class PlaceOrderResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
