package com.example.perfectweatherallyear.repository.remoteData.weatherData

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model.convertToDayWeather
import com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model.convertToHourWeather
import javax.inject.Inject

class ForecastApiComDataSource @Inject constructor(weatherApiCom: WeatherApiCom) : RemoteWeatherDataSource() {

    private val remoteService: WeatherApiCom by lazy {
        weatherApiCom
    }

    override suspend fun getWeatherForecast(location: Location, daysAmount: Int): List<DayWeather> {
        val forecast = remoteService.getWeatherForecast(location.name, daysAmount)
        return forecast.convertToDayWeather(location.id)
    }

    override suspend fun getHourlyWeather(daysAmount: Int, dayWeather: DayWeather, cityName: String): List<HourWeather> {
        val weekWeather = remoteService.getWeatherForecast(cityName, daysAmount)
        return weekWeather.convertToHourWeather(dayWeather)
    }
}