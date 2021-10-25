package com.example.perfectweatherallyear.repository.WeatherData

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.Result

interface WeatherRepository {
    suspend fun getDayWeather(weekDay: String): Result<List<DayWeather>>

    suspend fun getWeekWeather(): Result<List<DayWeather>>
}