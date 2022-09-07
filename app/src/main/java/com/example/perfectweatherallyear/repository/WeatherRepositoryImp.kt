package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.api.ConnectionDetector
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.localData.LocationDataSource
import com.example.perfectweatherallyear.repository.localData.WeatherDataSource
import com.example.perfectweatherallyear.repository.remoteData.weatherData.RemoteWeatherDataSource
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteWeatherDataSource,
    private val weatherDataSource: WeatherDataSource,
    private val locationDataSource: LocationDataSource,
    private var connectionDetector: ConnectionDetector
) : WeatherRepository {

    override suspend fun getWeatherForecast(location: Location, daysAmount: Int): DataResult<List<DayWeather>> {
        return if (connectionDetector.isConnectingToInternet())
            try {
                val remoteWeekWeather =
                    remoteDataSource.getWeatherForecast(location, daysAmount)
                weatherDataSource.insertDayWeather(remoteWeekWeather)

                val localWeekWeather =
                    weatherDataSource.getWeatherForecast(location.id, daysAmount)
                DataResult.Ok(localWeekWeather)
            } catch (e: Exception) {
                DataResult.Error(e.message.toString())
            }
        else {
            DataResult.Ok(weatherDataSource.getWeatherForecast(location.id, daysAmount))
        }
    }

    override suspend fun getHourlyWeather(
        daysAmount: Int, dayWeather: DayWeather
    ): DataResult<List<HourWeather>> {
        return if (connectionDetector.isConnectingToInternet())
            try {
                val cityName = locationDataSource.getCityNameByLocationId(dayWeather.cityId)
                val remoteHourlyWeather = remoteDataSource.getHourlyWeather(daysAmount, dayWeather, cityName)
                weatherDataSource.insertHourlyWeather(remoteHourlyWeather)
                val localHourWeather = weatherDataSource.getHourlyWeather(dayWeather.id)
                DataResult.Ok(localHourWeather)
            } catch (e: Exception) {
                DataResult.Error(e.message.toString())
            }
        else {
            DataResult.Ok(weatherDataSource.getHourlyWeather(dayWeather.id))
        }
    }

    override suspend fun insertDayWeather(dataWeatherData: List<DayWeather>) {
        weatherDataSource.insertDayWeather(dataWeatherData)
    }
}
