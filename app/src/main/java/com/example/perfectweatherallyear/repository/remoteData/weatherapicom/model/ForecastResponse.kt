package com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    val location : Any,
    val current: Any,
    val forecast: Forecast
)

data class Forecast(
    @SerializedName("forecastday")
    val forecastDay: List<DayItem>
)

data class DayItem(
    val date: String,
    val day: Day
)

data class Day(
    @SerializedName("maxtemp_c")
    val maxTemp: String,
    @SerializedName("mintemp_c")
    val minTemp: String
)