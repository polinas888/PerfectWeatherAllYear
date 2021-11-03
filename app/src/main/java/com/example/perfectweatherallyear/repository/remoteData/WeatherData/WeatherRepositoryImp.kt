package com.example.perfectweatherallyear.repository.remoteData.WeatherData

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRepositoryImp {

    @GET("history.json")
    suspend fun getDayWeather(@Query("q") city: String, @Query("dt") date: Int): List<DayWeather>

    @GET("forecast.json")
    suspend fun getWeekWeather(@Query("q") city: String, @Query("days") daysAmount: Int): Response<ForecastResponse>
}