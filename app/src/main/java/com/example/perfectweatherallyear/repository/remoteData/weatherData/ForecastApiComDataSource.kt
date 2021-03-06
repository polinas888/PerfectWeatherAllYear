package com.example.perfectweatherallyear.repository.remoteData.weatherData

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.repository.localData.LocationDao
import com.example.perfectweatherallyear.repository.localData.WeatherDao
import com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model.convertToDayWeather
import com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model.convertToHourWeather
import javax.inject.Inject

class ForecastApiComDataSource @Inject constructor(weatherApiCom: WeatherApiCom, weatherDao: WeatherDao, locationDao: LocationDao) : RemoteWeatherDataSource() {

    private val remoteService: WeatherApiCom by lazy {
        weatherApiCom
    }

    private val localWeatherService: WeatherDao by lazy {
        weatherDao
    }

    private val localLocationService: LocationDao by lazy {
        locationDao
    }

    override suspend fun getWeatherForecast(city: String, daysAmount: Int): List<DayWeather> {
        val forecast = remoteService.getWeatherForecast(city, daysAmount)
        val cityId = localLocationService.getLocationIdByCityName(city)
        return forecast.convertToDayWeather(cityId)
    }

    override suspend fun getHourlyWeather(daysAmount: Int, dayWeather: DayWeather, cityName: String): List<HourWeather> {
        val weekWeather = remoteService.getWeatherForecast(cityName, daysAmount)
        return weekWeather.convertToHourWeather(dayWeather)
    }
}