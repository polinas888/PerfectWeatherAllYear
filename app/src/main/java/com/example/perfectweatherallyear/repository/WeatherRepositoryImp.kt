package com.example.perfectweatherallyear.repository

import android.content.Context
import com.example.perfectweatherallyear.api.ConnectionDetector
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.localData.LocalLocationDataSourceImpl
import com.example.perfectweatherallyear.repository.localData.LocalWeatherDataSource
import com.example.perfectweatherallyear.repository.remoteData.weatherData.RemoteWeatherDataSource
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteWeatherDataSource,
    private val localWeatherDataSource: LocalWeatherDataSource,
    private val localLocationDataSourceImpl: LocalLocationDataSourceImpl,
    context: Context
) : WeatherRepository {
    private val mConnectionDetector: ConnectionDetector = ConnectionDetector(context)

    override suspend fun getWeatherForecast(location: Location, daysAmount: Int): DataResult<List<DayWeather>> {
        return if (mConnectionDetector.isConnectingToInternet())
            try {
                val remoteWeekWeather =
                    remoteDataSource.getWeatherForecast(location.name, daysAmount)
                localWeatherDataSource.insertDayWeather(remoteWeekWeather)

                val localWeekWeather =
                    localWeatherDataSource.getWeatherForecast(location.id, daysAmount)
                DataResult.Ok(localWeekWeather)
            } catch (e: Exception) {
                DataResult.Error(e.message.toString())
            }
        else {
            DataResult.Ok(localWeatherDataSource.getWeatherForecast(location.id, daysAmount))
        }
    }

    override suspend fun getHourlyWeather(
        daysAmount: Int, dayWeather: DayWeather
    ): DataResult<List<HourWeather>> {
        return if (mConnectionDetector.isConnectingToInternet())
            try {
                val cityName = localLocationDataSourceImpl.getCityNameByLocationId(dayWeather.cityId)
                val remoteHourlyWeather = remoteDataSource.getHourlyWeather(daysAmount, dayWeather, cityName)
                localWeatherDataSource.insertHourlyWeather(remoteHourlyWeather)
                val localHourWeather = localWeatherDataSource.getHourlyWeather(dayWeather.id)
                DataResult.Ok(localHourWeather)
            } catch (e: Exception) {
                DataResult.Error(e.message.toString())
            }
        else {
            DataResult.Ok(localWeatherDataSource.getHourlyWeather(dayWeather.id))
        }
    }
}
