package com.example.perfectweatherallyear.repository.remote.forecast

import com.example.perfectweatherallyear.repository.remote.forecast2.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {
    @GET("forecast.json")
    suspend fun fetchForecast(
        @Query("q") city: String,
        @Query("days") days: String
    ): ForecastResponse
}