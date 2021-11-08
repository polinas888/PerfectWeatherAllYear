package com.example.perfectweatherallyear.repository.remoteData.WeatherData

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.DataResult

abstract class RemoteWeatherDataSource {

    abstract suspend fun getWeekWeather(city: String, daysAmount: Int): DataResult<List<DayWeather>>
}