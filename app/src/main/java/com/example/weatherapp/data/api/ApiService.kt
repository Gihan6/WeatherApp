package com.example.weatherapp.data.api

import com.example.weatherapp.data.networkModels.response.CityDetailResponse
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

//    @POST("user.getsessiontoken")
//    @Headers("Content-Type: application/json")
//    suspend fun getSessionToken(@Body request: SessionTokenRequest): SessionTokenResponse
//
    @GET("weather")
    suspend fun getCityDetail(@Query("q")q:String, @Query("appid") appid:String): CityDetailResponse



}