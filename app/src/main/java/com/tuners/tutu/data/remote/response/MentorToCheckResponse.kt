package com.tuners.tutu.data.remote.response

import com.google.gson.annotations.SerializedName

data class MentorToCheckResponse(

	@field:SerializedName("result")
	val result: List<MentorsToCheck>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class MentorsToCheck(

	@field:SerializedName("mentorId")
	val mentorId: String,

	@field:SerializedName("username")
	val username: String
)
