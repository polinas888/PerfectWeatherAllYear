package com.example.perfectweatherallyear.repository.remoteData.weatherData

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.DataResult

abstract class RemoteWeatherDataSource {

    abstract suspend fun getWeekWeather(city: String, daysAmount: Int): DataResult<List<DayWeather>>
}