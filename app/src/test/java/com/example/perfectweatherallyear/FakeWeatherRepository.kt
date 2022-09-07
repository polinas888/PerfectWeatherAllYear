package com.example.perfectweatherallyear

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.WeatherRepository

class FakeWeatherRepository(private val remoteForecastWeather: MutableList<DayWeather>): WeatherRepository {
    private var weatherServiceData: LinkedHashSet<DayWeather> = linkedSetOf()
    var connectionDetector: FakeConnectionDetector = FakeConnectionDetector()

    override suspend fun getWeatherForecast(location: Location, daysAmount: Int): DataResult<List<DayWeather>> {
        if(connectionDetector.isConnectingToInternet()) {
            weatherServiceData.addAll(remoteForecastWeather)
        }
        var dayCount = 0
        val dayWeatherResult = mutableListOf<DayWeather>()
        for (dayWeather in weatherServiceData) {
            if (dayWeather.cityId == location.id && dayCount < daysAmount) {
                dayWeatherResult.add(dayWeather)
                dayCount++
            }
        }
        return DataResult.Ok(dayWeatherResult)
    }

    override suspend fun getHourlyWeather(
        daysAmount: Int,
        dayWeather: DayWeather
    ): DataResult<List<HourWeather>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertDayWeather(dataWeatherData: List<DayWeather>) {
        for (dayWeather in dataWeatherData) {
            weatherServiceData.add(dayWeather)
        }
    }
}