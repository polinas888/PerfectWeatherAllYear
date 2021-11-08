package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.remoteData.WeatherData.ForecastApiComDataSource

class WeatherRepositoryImp(private val remoteDataSource: ForecastApiComDataSource) : WeatherRepository {
    override suspend fun getWeekWeather(city: String, daysAmount: Int): DataResult<List<DayWeather>> {
        return remoteDataSource.getWeekWeather(city, daysAmount)
    }
}
