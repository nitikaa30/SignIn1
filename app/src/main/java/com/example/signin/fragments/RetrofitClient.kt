package com.example.signin.fragments

import com.example.signin.API
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.Base64

object RetrofitClient {
//    private val auth="Basic " + Base64.encodeToString("Developer5@gmail.com:123456".toByteArray(),
//        Base64.NO_WRAP)
//    private const val BASE_URL="http://restapi.adequateshop.com/api/"
//    private val okHttpClient= OkHttpClient.Builder().addInterceptor { chain ->
//        val original=chain.request()
//        val requestBuilder=original.newBuilder().addHeader("Authorization",auth)
//            .method(original.method(),original.body())
//        val request=requestBuilder.build()
//        chain.proceed(request)
//    }.build()
//    val instance: API by lazy {
//        val retrofit= Retrofit.Builder()
//            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient).build()
//
//        retrofit.create(API::class.java)
//    }

    private val retrofit by lazy{
        Retrofit.Builder().baseUrl("http://restapi.adequateshop.com/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    val apiInterface by lazy{
        retrofit.create(API::class.java)
    }
}