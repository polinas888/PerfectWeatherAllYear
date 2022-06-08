package com.example.perfectweatherallyear.repository.localData

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class LocalWeatherDataSource @Inject constructor(weatherDao: WeatherDao) : WeatherDao {
     private val localService: WeatherDao by lazy {
        weatherDao
    }

    override fun insertDayWeather(dataWeatherData: List<DayWeather>): Completable {
        return localService.insertDayWeather(dataWeatherData)
    }

    override fun insertHourlyWeather(dataWeatherData: List<HourWeather>) {
        localService.insertHourlyWeather(dataWeatherData)
    }

    override fun getDayWeatherByCityAndDate(city: Int, date: String): Flowable<DayWeather> {
        return localService.getDayWeatherByCityAndDate(city, date)
    }

    override fun getWeatherForecast(cityId: Int, daysAmount: Int): Flowable<List<DayWeather>> {
        return localService.getWeatherForecast(cityId, daysAmount)
    }

    override fun getHourlyWeather(dayWeatherId: Int): Flowable<List<HourWeather>> {
        return localService.getHourlyWeather(dayWeatherId)
    }
}