package com.example.perfectweatherallyear.repository.remoteData.weatherData

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import io.reactivex.Observable

abstract class RemoteWeatherDataSource {

    abstract fun getWeatherForecast(city: String, daysAmount: Int): Observable<List<DayWeather>>
    abstract fun getHourlyWeather(daysAmount: Int, dayWeather: DayWeather, cityName: String): Observable<List<HourWeather>>
}