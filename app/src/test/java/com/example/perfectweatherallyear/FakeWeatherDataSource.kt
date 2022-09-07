package com.example.perfectweatherallyear

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.GeneralWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.repository.localData.WeatherDataSource

class FakeWeatherDataSource(private val dayWeatherList: MutableList<DayWeather> = mutableListOf()) :
    WeatherDataSource {

    override suspend fun insertDayWeather(dataWeatherData: List<DayWeather>) {
        for (dayWeather in dataWeatherData) {
            dayWeatherList.add(dayWeather)
        }
    }

    override suspend fun insertHourlyWeather(dataWeatherData: List<HourWeather>) {
        TODO("Not yet implemented")
    }

    override suspend fun getDayWeather(): List<DayWeather> {
        return dayWeatherList
    }

    override suspend fun getDayWeatherByCityAndDate(cityId: Int, date: String): DayWeather {
        var dayWeatherResult = DayWeather(
            0, "00-00-0000", 0,
            GeneralWeather("0", "0", "0", "0")
        )
        for (dayWeather in dayWeatherList) {
            if (dayWeather.id == cityId && dayWeather.date == date) {
                dayWeatherResult = dayWeather
            }
        }
        return dayWeatherResult
    }

    override suspend fun getWeatherForecast(
        cityId: Int,
        daysAmount: Int
    ): List<DayWeather> {
        var dayCount = 0
        val dayWeatherResult = mutableListOf<DayWeather>()
        for (dayWeather in dayWeatherList) {
            if (dayWeather.cityId == cityId && dayCount < daysAmount) {
                dayWeatherResult.add(dayWeather)
                dayCount++
            }
        }
        return dayWeatherResult
    }

    override suspend fun getHourlyWeather(dayWeatherId: Int): List<HourWeather> {
        TODO("Not yet implemented")
    }
}