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

    override suspend fun updateDayWeather(dataWeatherData: List<DayWeather>) {
        localService.updateDayWeather(dataWeatherData)
    }

    override suspend fun insertHourlyWeather(dataWeatherData: List<HourWeather>) {
        localService.insertHourlyWeather(dataWeatherData)
    }

    override suspend fun getDayWeatherByCityAndDate(city: Int, date: String): DayWeather {
        return localService.getDayWeatherByCityAndDate(city, date)
    }

    override suspend fun getWeatherForecast(cityId: Int, daysAmount: Int): List<DayWeather> {
        return localService.getWeatherForecast(cityId, daysAmount)
    }

    override suspend fun getHourlyWeather(dayWeatherId: Int): List<HourWeather> {
        return localService.getHourlyWeather(dayWeatherId)
    }
}