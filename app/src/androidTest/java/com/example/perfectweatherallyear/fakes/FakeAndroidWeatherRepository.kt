package com.example.perfectweatherallyear.fakes

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.GeneralWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.WeatherRepository

class FakeAndroidWeatherRepository: WeatherRepository {
    private val forecastWeather = mutableListOf(
        DayWeather(
            1, "10-01-2000", 1,
            GeneralWeather("+7", "+15", "3", "11")
        )
    )

    override suspend fun getWeatherForecast(location: Location, daysAmount: Int): DataResult<List<DayWeather>> {
        return DataResult.Ok(forecastWeather)
    }

    override suspend fun getHourlyWeather(
        daysAmount: Int,
        dayWeather: DayWeather
    ): DataResult<List<HourWeather>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertDayWeather(dataWeatherData: List<DayWeather>) {
        TODO("Not yet implemented")
    }
}