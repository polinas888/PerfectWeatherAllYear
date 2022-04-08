package com.example.perfectweatherallyear.repository.remote

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.remote.forecast.ForecastDataSource

class ForecastRepository {
    private val forecastDataSource: ForecastDataSource = ForecastDataSource()

    suspend fun fetchForecast(city: String, days: String): DataResult<List<DayWeather>> {
        return forecastDataSource.fetchForecast(city, days)
    }
}