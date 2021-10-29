package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.remoteData.WeatherData.RemoteDataSource
import java.time.LocalDate

class Repository(private val remoteDataSource: RemoteDataSource) {

    suspend fun getDayWeather(city: String, data: LocalDate): DataResult<List<DayWeather>> {
        return try {
            val response = remoteDataSource.getDayWeather(city, data)
            DataResult.Ok(response)
        } catch (ex: java.lang.Exception) {
            DataResult.Error(ex.message)
        }
    }

    suspend fun getWeekWeather(city: String, daysAmount: Int): DataResult<List<DayWeather>> {
        return try {
            val response = remoteDataSource.getWeekWeather(city, daysAmount)
            DataResult.Ok(response)
        } catch (ex: java.lang.Exception) {
            DataResult.Error(ex.message)
        }
    }
}
