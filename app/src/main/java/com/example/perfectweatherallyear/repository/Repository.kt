package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.remoteData.WeatherData.RemoteDataSource

class Repository(private val remoteDataSource: RemoteDataSource) {

    suspend fun getWeekWeather(city: String, daysAmount: Int): DataResult<List<DayWeather>> {
        return remoteDataSource.getWeekWeather(city, daysAmount)
    }
}
