package com.example.perfectweatherallyear.repository.remoteData.WeatherData

import com.example.perfectweatherallyear.model.DayWeather
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

class RemoteDataSource {
    private val BASE_URL = "http://api.weatherapi.com/v1/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val remoteService: WeatherRepositoryImp by lazy {
        retrofit.create(WeatherRepositoryImp::class.java)
    }

    suspend fun getDayWeather(city: String, data: LocalDate): List<DayWeather> = remoteService.getDayWeather(city, data)

    suspend fun getWeekWeather(city: String, daysAmount: Int): List<DayWeather> = remoteService.getWeekWeather(city, daysAmount)
}