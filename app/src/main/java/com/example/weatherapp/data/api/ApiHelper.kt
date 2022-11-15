package com.example.weatherapp.data.api

import com.example.weatherapp.data.networkModels.request.CityDetailRequest


class ApiHelper (private val apiService: ApiService) {
    suspend fun getCityDetail(request: CityDetailRequest) = apiService.getCityDetail(request.q,request.appid)


}