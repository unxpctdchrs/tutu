package com.tuners.tutu.data.remote.response

import com.google.gson.annotations.SerializedName

data class MentorListResponse(

	@field:SerializedName("mentors")
	val mentors: List<MentorsItem>,

	@field:SerializedName("error")
	val error: Boolean
)

data class MentorsItem(

	@field:SerializedName("rating")
	val rating: Float,

	@field:SerializedName("username")
	val username: String
)
