package com.example.perfectweatherallyear.repository.localData

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather

interface WeatherDataSource {
    suspend fun insertDayWeather(dataWeatherData: List<DayWeather>)

    suspend fun insertHourlyWeather(dataWeatherData: List<HourWeather>)

    suspend fun getDayWeather(): List<DayWeather>

    suspend fun getDayWeatherByCityAndDate(cityId: Int, date: String): DayWeather

    suspend fun getWeatherForecast(cityId: Int, daysAmount: Int): List<DayWeather>

    suspend fun getHourlyWeather(dayWeatherId: Int): List<HourWeather>
}