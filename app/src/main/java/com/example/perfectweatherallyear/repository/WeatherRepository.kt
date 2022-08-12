package com.example.perfectweatherallyear.repository

import androidx.lifecycle.MutableLiveData
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location

interface WeatherRepository {
    fun updateForecastWeather(location: Location, daysAmount: Int)
    fun updateHourWeather(dayWeather: DayWeather)
    fun getLocalWeatherForecastLiveData(location: Location, numDays: Int) : MutableLiveData<List<DayWeather>>
    fun getLocalHourWeatherLiveData(dayWeather: DayWeather) : MutableLiveData<List<HourWeather>>
    fun getRemoteWeatherForecastLiveData() : MutableLiveData<List<DayWeather>>
    fun getRemoteHourWeatherLiveData(): MutableLiveData<List<HourWeather>>
    fun clear()
}