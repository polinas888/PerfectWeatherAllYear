package com.example.perfectweatherallyear.repository.remoteData.WeatherData

import com.example.perfectweatherallyear.model.DayWeather
import retrofit2.http.GET
import retrofit2.http.Path
import java.time.LocalDate

interface WeatherRepositoryImp {

    @GET("history/{city, data}")
    suspend fun getDayWeather(@Path("city") city: String, @Path("date") date: LocalDate): List<DayWeather>

    @GET("forecast/{city, daysAmount}")
    suspend fun getWeekWeather(@Path("city") city: String, @Path("daysAmount") date: Int): List<DayWeather>
}