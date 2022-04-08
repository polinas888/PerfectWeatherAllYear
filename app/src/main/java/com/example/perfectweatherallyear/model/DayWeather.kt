package com.example.perfectweatherallyear.model

import com.google.gson.annotations.SerializedName

data class DayWeather(
    @SerializedName("date")
    val date: String,
    @SerializedName("maxtemp_c")
    val max_temperature: Double,
    @SerializedName("mintemp_c")
    val min_temperature: Double,
    @SerializedName("totalprecip_mm")
    val precipitation: Double,
    @SerializedName("wind_kph")
    val wind: Double
)