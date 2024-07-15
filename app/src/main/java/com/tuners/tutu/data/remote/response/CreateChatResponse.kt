package com.tuners.tutu.data.remote.response

import com.google.gson.annotations.SerializedName

data class CreateChatResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("chatroomId")
	val chatroomId: String? = null
)
