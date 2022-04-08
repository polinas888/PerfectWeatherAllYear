package com.example.perfectweatherallyear.repository.remote.forecast

import com.example.perfectweatherallyear.api.ApiFactory
import com.example.perfectweatherallyear.repository.remote.forecast2.ForecastResponse

class ForecastDataSource {
    private val remoteService: ForecastApi by lazy {
        val createWeatherApi = ApiFactory.createWeatherApi()
        createWeatherApi
    }

    suspend fun fetchForecast(city: String, days: String): ForecastResponse =
       remoteService.fetchForecast(city,days)
}