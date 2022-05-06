package com.example.perfectweatherallyear.repository

import android.content.Context
import com.example.perfectweatherallyear.api.ConnectionDetector
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.localData.LocalWeatherDataSource
import com.example.perfectweatherallyear.repository.remoteData.weatherData.RemoteWeatherDataSource
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(private val remoteDataSource: RemoteWeatherDataSource,
    private val localWeatherDataSource: LocalWeatherDataSource, context: Context) : WeatherRepository {
    private val mConnectionDetector: ConnectionDetector = ConnectionDetector(context)

    override suspend fun getWeatherForecast(
        location: Location,
        daysAmount: Int
    ): DataResult<List<DayWeather>> {
        return if (mConnectionDetector.isConnectingToInternet())
            try {
               val remoteWeekWeather =
                    remoteDataSource.getWeatherForecast(location.name, daysAmount)
                val localWeatherForecast =
                    localWeatherDataSource.getWeatherForecast(location.id, daysAmount)
                if(localWeatherForecast.isEmpty())
                localWeatherDataSource.insertDayWeather(remoteWeekWeather)
                else
                    localWeatherDataSource.updateDayWeather(remoteWeekWeather)

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
        daysAmount: Int, cityId: Int, date: String, dayWeatherId: Int
    ): DataResult<List<HourWeather>> {
        return if (mConnectionDetector.isConnectingToInternet())
            try {
                val hourlyWeather = remoteDataSource.getHourlyWeather(daysAmount, cityId, date)
                localWeatherDataSource.insertHourlyWeather(hourlyWeather)
                val localHourWeather = localWeatherDataSource.getHourlyWeather(dayWeatherId)
                DataResult.Ok(localHourWeather)
            } catch (e: Exception) {
                DataResult.Error(e.message.toString())
            }
        else {
            DataResult.Ok(localWeatherDataSource.getHourlyWeather(dayWeatherId))
        }
    }
}
