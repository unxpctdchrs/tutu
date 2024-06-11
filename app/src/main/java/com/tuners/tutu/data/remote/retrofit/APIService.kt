package com.tuners.tutu.data.remote.retrofit

import com.tuners.tutu.data.remote.response.LoginResponse
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
        @Field("jenjangPendidikan") jenjangPendidikan: String
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
        @Field("jenjangPendidikan") jenjangPendidikan: String
    ): Call<UserUpdateResponse>
}