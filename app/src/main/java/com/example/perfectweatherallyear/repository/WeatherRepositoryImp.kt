package com.example.perfectweatherallyear.repository

import android.content.Context
import com.example.perfectweatherallyear.api.ConnectionDetector
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.localData.LocalLocationDataSource
import com.example.perfectweatherallyear.repository.localData.LocalWeatherDataSource
import com.example.perfectweatherallyear.repository.remoteData.weatherData.RemoteWeatherDataSource
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(private val remoteDataSource: RemoteWeatherDataSource, private val localWeatherDataSource: LocalWeatherDataSource,
                                               private val localLocationDataSource: LocalLocationDataSource, context: Context) : WeatherRepository {
    private val mConnectionDetector: ConnectionDetector = ConnectionDetector(context)

    override fun getWeatherForecast(location: Location, daysAmount: Int): Flowable<List<DayWeather>> {
        return if (mConnectionDetector.isConnectingToInternet()) {
                remoteDataSource.getWeatherForecast(location.name, daysAmount)
                    .map { localWeatherDataSource.insertDayWeather(it) }
                    .flatMap { localWeatherDataSource.getWeatherForecast(location.id, daysAmount) }
        } else {
            localWeatherDataSource.getWeatherForecast(location.id, daysAmount)
        }
    }

//    override suspend fun getHourlyWeather(
//        daysAmount: Int, dayWeather: DayWeather
//    ): DataResult<List<HourWeather>> {
//        return if (mConnectionDetector.isConnectingToInternet())
//            try {
//                val cityName = localLocationDataSource.getCityNameByCityId(dayWeather.cityId)
//                val remoteHourlyWeather =
//                    remoteDataSource.getHourlyWeather(daysAmount, dayWeather, cityName)
//                localWeatherDataSource.insertHourlyWeather(remoteHourlyWeather)
//                val localHourWeather = localWeatherDataSource.getHourlyWeather(dayWeather.id)
//                DataResult.Ok(localHourWeather)
//            } catch (e: Exception) {
//                DataResult.Error(e.message.toString())
//            }
//        else {
//            DataResult.Ok(localWeatherDataSource.getHourlyWeather(dayWeather.id))
//        }
//    }
}
