package com.tuners.tutu.data.remote.response

import com.google.gson.annotations.SerializedName

data class MentorListResponse(

	@field:SerializedName("result")
	val result: List<ResultItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ResultItem(

	@field:SerializedName("rating")
	val rating: Float? = null,

	@field:SerializedName("mentorId")
	val mentorId: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
