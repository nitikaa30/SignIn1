package com.example.signin

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {

    @POST("authaccount/registration")
    fun registerUser(@Body userData: Register): Call<RegisterResponse>

    @GET("users")
    fun getInfo():Call<RegisterResponse>

}