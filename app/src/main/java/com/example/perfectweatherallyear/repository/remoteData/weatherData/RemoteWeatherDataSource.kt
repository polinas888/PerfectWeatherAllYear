package com.example.perfectweatherallyear.repository.remoteData.weatherData

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather

abstract class RemoteWeatherDataSource {

    abstract fun getWeatherForecast(city: String, daysAmount: Int): List<DayWeather>
    abstract suspend fun getHourlyWeather(daysAmount: Int, dayWeather: DayWeather, cityName: String): List<HourWeather>
}