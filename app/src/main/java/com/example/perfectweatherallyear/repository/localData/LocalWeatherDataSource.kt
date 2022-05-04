package com.example.perfectweatherallyear.repository.localData

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import javax.inject.Inject

class LocalWeatherDataSource @Inject constructor(weatherDao: WeatherDao) : WeatherDao {
     private val localService: WeatherDao by lazy {
        weatherDao
    }

    override suspend fun insertDayWeather(dataWeatherData: List<DayWeather>) {
        localService.insertDayWeather(dataWeatherData)
    }

    override suspend fun insertHourlyWeather(dataWeatherData: List<HourWeather>) {
        localService.insertHourlyWeather(dataWeatherData)
    }

    override suspend fun getDayWeatherByCityAndDate(city: String, date: String): DayWeather {
        return localService.getDayWeatherByCityAndDate(city, date)
    }

    override suspend fun getWeatherForecast(city: String, daysAmount: Int): List<DayWeather> {
        return localService.getWeatherForecast(city, daysAmount)
    }

    override suspend fun getHourlyWeather(): List<HourWeather> {
        return localService.getHourlyWeather()
    }
}