package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.model.DayWeather

interface WeatherRepository {
    suspend fun getWeekWeather(city: String, daysAmount: Int): DataResult<List<DayWeather>>
}