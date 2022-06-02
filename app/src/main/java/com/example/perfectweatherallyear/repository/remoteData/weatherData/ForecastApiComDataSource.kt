package com.example.perfectweatherallyear.repository.remoteData.weatherData

import android.util.Log
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.repository.localData.LocationDao
import com.example.perfectweatherallyear.repository.localData.WeatherDao
import com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model.convertToDayWeather
import com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model.convertToHourWeather
import java.util.concurrent.Executors
import javax.inject.Inject

class ForecastApiComDataSource @Inject constructor(
    weatherApiCom: WeatherApiCom,
    weatherDao: WeatherDao,
    locationDao: LocationDao
) : RemoteWeatherDataSource() {

    private val executor = Executors.newSingleThreadExecutor()

    private val remoteService: WeatherApiCom by lazy {
        weatherApiCom
    }

    private val localWeatherService: WeatherDao by lazy {
        weatherDao
    }

    private val localLocationService: LocationDao by lazy {
        locationDao
    }

    override fun getWeatherForecast(city: String, daysAmount: Int): List<DayWeather> {
        val forecast = remoteService.getWeatherForecast(city, daysAmount)
        val cityId = localLocationService.getLocationIdByCityName(city)
        return forecast.convertToDayWeather(cityId)
    }

    override suspend fun getHourlyWeather(
        daysAmount: Int, dayWeather: DayWeather, cityName: String
    ): List<HourWeather> {
        var hourWeatherList = listOf<HourWeather>()
        val future = executor.submit {
            try {
                val weekWeather = remoteService.getWeatherForecast(cityName, daysAmount)
                val convertToHourWeather = weekWeather.convertToHourWeather(dayWeather)
                hourWeatherList = convertToHourWeather
            } catch (exception: Exception) {
                Log.i("future", exception.message.toString())
            }
        }
        future.get()
        return hourWeatherList
    }
}