package com.example.perfectweatherallyear.repository.remoteData.weatherData

import com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model.ForecastResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiCom {

    @GET("forecast.json")
    fun getWeatherForecast(@Query("q") city: String, @Query("days") daysAmount: Int): Observable<ForecastResponse>
}