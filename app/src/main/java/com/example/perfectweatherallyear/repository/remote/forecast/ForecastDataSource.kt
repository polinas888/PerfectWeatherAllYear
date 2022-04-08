package com.example.perfectweatherallyear.repository.remote.forecast

import com.example.perfectweatherallyear.api.ApiFactory
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.FailureResult
import com.example.perfectweatherallyear.repository.SuccessResult
import com.example.perfectweatherallyear.repository.remote.forecast2.ForecastPerDay

class ForecastDataSource {
    private val remoteService: ForecastApi by lazy {
        val createWeatherApi = ApiFactory.createWeatherApi()
        createWeatherApi
    }

    suspend fun fetchForecast(city: String, days: String): DataResult<List<DayWeather>> {
        return try {
            val data = remoteService.fetchForecast(city, days)
            val weatherList = convertForecastResponseToDayWeather(data.forecast.forecastList)
            SuccessResult(weatherList)
        } catch (e: Exception) {
            FailureResult(e.message.toString())
        }
    }

    private fun convertForecastResponseToDayWeather(forecastList: List<ForecastPerDay>): List<DayWeather> {
        val weatherList = mutableListOf<DayWeather>()
        for (forecastItem in forecastList) {
            weatherList.add(
                DayWeather(
                    forecastItem.date,
                    forecastItem.dayWeather.max_temperature,
                    forecastItem.dayWeather.min_temperature,
                    forecastItem.dayWeather.precipitation,
                    forecastItem.dayWeather.wind)
            )
        }
        return weatherList
    }
}