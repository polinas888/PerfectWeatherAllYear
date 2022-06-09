package com.example.perfectweatherallyear.repository.remoteData.weatherData

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.localData.LocationDao
import com.example.perfectweatherallyear.repository.localData.WeatherDao
import com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model.convertToDayWeather
import io.reactivex.rxjava3.core.Flowable
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

    override fun getWeatherForecast(city: String, daysAmount: Int): Flowable<List<DayWeather>> {
        val forecast = remoteService.getWeatherForecast(city, daysAmount)
        val cityId = localLocationService.getLocationIdByCityName(city)
        val listDayWeather = Flowable.combineLatest(forecast, cityId)
        { forecast, cityIdFlowable ->
            forecast.convertToDayWeather(cityIdFlowable) }
        return listDayWeather
//
//        remoteService.getWeatherForecast(city, daysAmount).flatMap { localLocationService.getLocationIdByCityName(city) }
//            .combineLatest()
//
//        remoteService.getWeatherForecast(city, daysAmount)
//            .flatMap { forecast -> forecast.convertToDayWeather(localLocationService.getLocationIdByCityName(city)) }

//        val forecast = remoteService.getWeatherForecast(city, daysAmount)
//        val cityIdFlowable = localLocationService.getLocationIdByCityName(city)
//        val flatMap = cityIdFlowable.flatMap { id -> forecast.map { it.convertToDayWeather(id) } }
//        return flatMap
        }
    }

//forecast.map { it.convertToDayWeather(cityId) }
//        return cityIdSingle.toFlowable().flatMap { id ->
//            forecast.map { it.convertToDayWeather(id) }


//    override suspend fun getHourlyWeather(daysAmount: Int, dayWeather: DayWeather, cityName: String): Observable<List<HourWeather>> {
//        val weekWeather = remoteService.getWeatherForecast(cityName, daysAmount)
//        return weekWeather.convertToHourWeather(dayWeather)
//    }
