package com.tuners.tutu.data.remote.retrofit

import com.tuners.tutu.data.remote.response.ChatsResponse
import com.tuners.tutu.data.remote.response.CreateChatResponse
import com.tuners.tutu.data.remote.response.LoginResponse
import com.tuners.tutu.data.remote.response.MentorListResponse
import com.tuners.tutu.data.remote.response.MessageResponse
import com.tuners.tutu.data.remote.response.PlaceOrderResponse
import com.tuners.tutu.data.remote.response.RegisterResponse
import com.tuners.tutu.data.remote.response.UserDetailsResponse
import com.tuners.tutu.data.remote.response.UserUpdateResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("birthDatePlace") birthDatePlace: String,
        @Field("email") email: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("jenjangPendidikan") jenjangPendidikan: String,
        @Field("role") role: String
    ): Call<RegisterResponse>

    @GET("user/:userId")
    fun getUserDetails(): Call<UserDetailsResponse>

    @FormUrlEncoded
    @PATCH("user/{userId}")
    fun updateUser(
        @Path("userId") userId: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("birthDatePlace") birthDatePlace: String,
        @Field("email") email: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("jenjangPendidikan") jenjangPendidikan: String,
    ): Call<UserUpdateResponse>

    @GET("mentors")
    fun getMentors(): Call<MentorListResponse>

    // search mentor
    @GET("mentors/search")
    fun searchMentors(
        @Query("username") username: String
    ): Call<MentorListResponse>

    // user page
    @GET("chats/{userId}")
    fun getChats(
        @Path("userId") userId: String
    ): Call<ChatsResponse>

    //mentor page
    @GET("chats/mentor/{mentorId}")
    fun getChatsMentor(
        @Path("mentorId") mentorId: String
    ): Call<ChatsResponse>

    // create a chatroom
    @FormUrlEncoded
    @POST("chats")
    fun createChat(
        @Field("mentorUsername") mentorUsername: String,
        @Field("mentorId") mentorId: String,
        @Field("studentUsername") studentUsername: String,
        @Field("userId") userId: String
    ): Call<CreateChatResponse>

    @FormUrlEncoded
    @POST("message")
    fun postMessage(
        @Field("roomId") roomId: String,
        @Field("message") message: String,
        @Field("senderId") senderId: String
    ): Call<MessageResponse>

    @FormUrlEncoded
    @POST("order")
    fun placeOrder(
        @Field("mentorUsername") mentorUsername: String,
        @Field("userId") userId: String,
        @Field("consultationType") consultationType: String,
        @Field("consultationDuration") consultationDuration: String,
        @Field("total") total: Int
    ): Call<PlaceOrderResponse>

}