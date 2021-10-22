package com.example.perfectweatherallyear.repository.WeatherData

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.WeekDay

interface WeatherRepository {
    suspend fun getDayWeather(weekDay: WeekDay): List<DayWeather>

    suspend fun getWeekWeather(): List<DayWeather>
}