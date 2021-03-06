package com.example.perfectweatherallyear.repository.remoteData.weatherData

import com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiCom {

    @GET("forecast.json")
    suspend fun getWeatherForecast(@Query("q") city: String, @Query("days") daysAmount: Int): ForecastResponse
}