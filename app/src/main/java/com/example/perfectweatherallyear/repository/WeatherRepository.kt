package com.example.perfectweatherallyear.repository

import androidx.paging.PagingData
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeatherForecast(location: Location, daysAmount: Int): DataResult<List<DayWeather>>
    suspend fun getHourlyWeatherPaged(daysAmount: Int, dayWeather: DayWeather): Flow<DataResult<PagingData<HourWeather>>>
}