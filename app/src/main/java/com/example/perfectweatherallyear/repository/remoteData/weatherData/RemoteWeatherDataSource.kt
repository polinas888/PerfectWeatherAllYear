package com.example.perfectweatherallyear.repository.remoteData.weatherData

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location

abstract class RemoteWeatherDataSource {

    abstract suspend fun getWeatherForecast(location: Location, daysAmount: Int): List<DayWeather>
    abstract suspend fun getHourlyWeather(daysAmount: Int, dayWeather: DayWeather, cityName: String): List<HourWeather>
}