package com.tuners.tutu.data.remote.response

import com.google.gson.annotations.SerializedName

data class OrderHistoryResponse(

	@field:SerializedName("getUserOrders")
	val getUserOrders: List<GetUserOrdersItem>,

	@field:SerializedName("error")
	val error: Boolean
)

data class GetUserOrdersItem(

	@field:SerializedName("appFee")
	val appFee: Int,

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("consultationDuration")
	val consultationDuration: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("mentorUsername")
	val mentorUsername: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("consultationFee")
	val consultationFee: Int,

	@field:SerializedName("timestamp")
	val timestamp: String,

	@field:SerializedName("consultationType")
	val consultationType: String
)
