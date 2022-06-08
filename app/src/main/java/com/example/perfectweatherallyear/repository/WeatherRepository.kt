package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.Location
import io.reactivex.rxjava3.core.Flowable

interface WeatherRepository {
    fun getWeatherForecast(location: Location, daysAmount: Int): Flowable<List<DayWeather>>
 //   suspend fun getHourlyWeather(daysAmount: Int, dayWeather: DayWeather): DataResult<List<HourWeather>>
}