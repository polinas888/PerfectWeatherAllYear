package com.example.perfectweatherallyear.repository.WeatherData

import com.example.perfectweatherallyear.model.DayWeather

sealed class WeatherResult {
        data class Ok(val response: List<DayWeather>) : WeatherResult()
        data class Error(val error: String?) : WeatherResult()
    }
