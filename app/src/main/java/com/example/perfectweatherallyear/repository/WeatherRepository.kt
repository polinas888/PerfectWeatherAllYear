package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location

interface WeatherRepository {
    suspend fun getWeatherForecast(location: Location, daysAmount: Int): DataResult<List<DayWeather>>
    suspend fun getHourlyWeather(daysAmount: Int, cityId: Int, date: String, dayWeatherId: Int): DataResult<List<HourWeather>>
}