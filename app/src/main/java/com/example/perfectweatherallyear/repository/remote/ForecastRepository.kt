package com.example.perfectweatherallyear.repository.remote

import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.FailureResult
import com.example.perfectweatherallyear.repository.SuccessResult
import com.example.perfectweatherallyear.repository.remote.forecast.ForecastDataSource
import com.example.perfectweatherallyear.repository.remote.forecast2.ForecastResponse

class ForecastRepository {
    private val forecastDataSource: ForecastDataSource = ForecastDataSource()

    suspend fun fetchForecast(city: String, days: String): DataResult<ForecastResponse> {
        return try {
            val data = forecastDataSource.fetchForecast(city, days)
            SuccessResult(data)
        } catch (e: Exception) {
            FailureResult(e.message.toString())
        }
    }
}