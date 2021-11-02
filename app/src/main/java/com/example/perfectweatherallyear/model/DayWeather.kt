package com.example.perfectweatherallyear.model


data class DayWeather(
    val temperatureMin: String,
    val temperatureMax: String,
    val precipitation: Int,
    val wind: Int
)