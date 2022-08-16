package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location
import io.reactivex.Observable

interface WeatherRepository {
    fun getRemoteWeatherForecast(city: String, daysAmount: Int): Observable<List<DayWeather>>
    fun insertDayWeather(dataWeatherData: List<DayWeather>?)
    fun getLocalWeatherForecast(location: Location, numDays: Int) : Observable<List<DayWeather>>
    fun insertHourWeather(hourWeatherData: List<HourWeather>)
    fun getRemoteHourlyWeather(numDay: Int, dayWeather: DayWeather, cityName: String): Observable<List<HourWeather>>
    fun getLocalHourWeather(dayWeather: DayWeather) : Observable<List<HourWeather>>
    fun clear()
}