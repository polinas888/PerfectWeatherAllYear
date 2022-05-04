package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather

interface WeatherRepository {
    suspend fun getWeatherForecast(city: String, daysAmount: Int, cityId: String): DataResult<List<DayWeather>>
    suspend fun getHourlyWeather(daysAmount: Int, cityId: String, date: String): DataResult<List<HourWeather>>
}