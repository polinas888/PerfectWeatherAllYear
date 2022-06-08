package com.example.perfectweatherallyear.repository.remoteData.weatherData

import com.example.perfectweatherallyear.model.DayWeather
import io.reactivex.rxjava3.core.Flowable

abstract class RemoteWeatherDataSource {

    abstract fun getWeatherForecast(city: String, daysAmount: Int): Flowable<List<DayWeather>>
  //  abstract suspend fun getHourlyWeather(daysAmount: Int, dayWeather: DayWeather, cityName: String): List<HourWeather>
}