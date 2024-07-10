package com.tuners.tutu.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChatsResponse(

	@field:SerializedName("chats")
	val chats: List<ChatsItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class ChatsItem(

	@field:SerializedName("lastMsg")
	val lastMsg: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("mentorUsername")
	val mentorUsername: String,

	@field:SerializedName("mentorId")
	val mentorId: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("chatroomId")
	val chatroomId: String
)
