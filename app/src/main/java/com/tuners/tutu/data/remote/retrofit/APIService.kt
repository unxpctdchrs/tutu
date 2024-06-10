package com.tuners.tutu.data.remote.retrofit

import com.tuners.tutu.data.remote.response.LoginResponse
import com.tuners.tutu.data.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
}