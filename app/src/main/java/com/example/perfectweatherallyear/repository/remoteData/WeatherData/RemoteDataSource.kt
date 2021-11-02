package com.example.perfectweatherallyear.repository.remoteData.WeatherData

import com.example.perfectweatherallyear.ApiFactory.weatherApiRetrofit
import com.example.perfectweatherallyear.model.DayWeather

class RemoteDataSource {

    private val remoteService: WeatherRepositoryImp by lazy {
        weatherApiRetrofit(DayWeather::class.java, DayWeatherDeserializer())
            .create(WeatherRepositoryImp::class.java)
    }

    suspend fun getDayWeather(city: String, data: Int): List<DayWeather> = remoteService.getDayWeather(city, data)

    suspend fun getWeekWeather(city: String, daysAmount: Int): List<DayWeather> = remoteService.getWeekWeather(city, daysAmount)
}