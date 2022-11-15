package com.example.weatherapp.data.networkModels.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CityDetailRequest(
    @SerializedName("q")
    @Expose var q: String,
    @SerializedName("appid")
    @Expose var appid: String,
)
