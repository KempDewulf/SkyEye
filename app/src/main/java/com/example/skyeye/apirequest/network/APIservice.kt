package com.example.skyeye.apirequest.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://ron-swanson-quotes.herokuapp.com/v2/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface APIservice
{
    @GET("quotes")
    suspend fun getQuotes(): String
}

object skyEyeApi {
    val retrofitService: APIservice by lazy {
        retrofit.create(APIservice::class.java)
    }
}
