package com.example.perfectweatherallyear.fakes

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.remoteData.weatherData.RemoteWeatherDataSource

class FakeRemoteWeatherDataSource(private val dayWeatherList: MutableList<DayWeather> = mutableListOf()) : RemoteWeatherDataSource() {

    override suspend fun getWeatherForecast(location: Location, daysAmount: Int): List<DayWeather> {
        var dayCount = 0
        val dayWeatherResult = mutableListOf<DayWeather>()
        for (dayWeather in dayWeatherList) {
            if (dayWeather.cityId == location.id && dayCount < daysAmount) {
                dayWeatherResult.add(dayWeather)
                dayCount++
            }
        }
        return dayWeatherResult
    }

    override suspend fun getHourlyWeather(
        daysAmount: Int,
        dayWeather: DayWeather,
        cityName: String
    ): List<HourWeather> {
        TODO("Not yet implemented")
    }
}