package com.example.perfectweatherallyear.repository.remoteData.weatherData

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.repository.localData.LocationDao
import com.example.perfectweatherallyear.repository.localData.WeatherDao
import com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model.convertToDayWeather
import com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model.convertToHourWeather
import io.reactivex.Observable
import javax.inject.Inject

class ForecastApiComDataSource @Inject constructor(
    weatherApiCom: WeatherApiCom,
    weatherDao: WeatherDao,
    locationDao: LocationDao
) : RemoteWeatherDataSource() {

    private val remoteService: WeatherApiCom by lazy {
        weatherApiCom
    }

    private val localWeatherService: WeatherDao by lazy {
        weatherDao
    }

    private val localLocationService: LocationDao by lazy {
        locationDao
    }

    override fun getWeatherForecast(city: String, daysAmount: Int): Observable<List<DayWeather>> {
        return localLocationService.getLocationIdByCityName(city)
            .concatMap { cityId -> remoteService.getWeatherForecast(city, daysAmount)
            .map { forecastResponse -> forecastResponse.convertToDayWeather(cityId) } }
    }

    override fun getHourlyWeather(daysAmount: Int, dayWeather: DayWeather, cityName: String
    ): Observable<List<HourWeather>> {
        return remoteService.getWeatherForecast(cityName, daysAmount).map { forecastResponse ->
            forecastResponse.convertToHourWeather(dayWeather)
        }
    }
}