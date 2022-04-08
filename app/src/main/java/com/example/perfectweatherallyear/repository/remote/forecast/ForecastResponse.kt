package com.example.perfectweatherallyear.repository.remote.forecast2

import com.example.perfectweatherallyear.model.DayWeather
import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("forecast")
    val forecast: Forecast
)

data class Forecast(
    @SerializedName("forecastday")
    val forecastList: List<ForecastPerDay>
)

data class ForecastPerDay(
    @SerializedName("date")
    val date: String,
    @SerializedName("day")
    val dayWeather: DayWeather
)