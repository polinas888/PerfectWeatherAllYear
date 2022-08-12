package com.example.perfectweatherallyear.repository

import androidx.lifecycle.MutableLiveData
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location

interface WeatherRepository {
    fun getUpdatedRemoteForecastWeather(location: Location, daysAmount: Int) : MutableLiveData<List<DayWeather>>
    fun getUpdatedRemoteHourWeather(dayWeather: DayWeather) : MutableLiveData<List<HourWeather>>
    fun getLocalWeatherForecastLiveData(location: Location, numDays: Int) : MutableLiveData<List<DayWeather>>
    fun getLocalHourWeatherLiveData(dayWeather: DayWeather) : MutableLiveData<List<HourWeather>>
    fun clear()
}