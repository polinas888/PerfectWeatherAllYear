package com.example.perfectweatherallyear.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.perfectweatherallyear.api.ConnectionDetector
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.localData.LocalWeatherDataSource
import com.example.perfectweatherallyear.repository.remoteData.weatherData.RemoteWeatherDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteWeatherDataSource,
    private val localWeatherDataSource: LocalWeatherDataSource, context: Context
) : WeatherRepository {
    private val mConnectionDetector: ConnectionDetector = ConnectionDetector(context)

    override suspend fun getWeatherForecast(location: Location, daysAmount: Int): DataResult<List<DayWeather>> {
        return if (mConnectionDetector.isConnectingToInternet())
            try {
                val remoteWeekWeather =
                    remoteDataSource.getWeatherForecast(location.name, daysAmount)
                localWeatherDataSource.upsertForecast(remoteWeekWeather)

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

    override suspend fun getHourlyWeatherPaged(daysAmount: Int, dayWeather: DayWeather
    ): Flow<DataResult<PagingData<HourWeather>>>  {
        return if (mConnectionDetector.isConnectingToInternet())
            try {
                val remoteHourlyWeather = remoteDataSource.getHourlyWeather(daysAmount, dayWeather.cityId, dayWeather.date)

                localWeatherDataSource.upsertHourlyWeather(remoteHourlyWeather)
           //     DataResult.Ok(localWeatherDataSource.getHourlyWeatherPaged(dayWeather.id))
                getHourlyWeatherPagingData(dayWeather.id)
                  .map {
                      DataResult.Ok(it)
                  }
            } catch (e: Exception) {
                flowOf(DataResult.Error(e.message.toString()))
            }
        else {
            getHourlyWeatherPagingData(dayWeather.id)
              .map {
                  DataResult.Ok(it)
              }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun getHourlyWeatherPagingData(dayWeatherId: Int): Flow<PagingData<HourWeather>> {
        return Pager(
            PagingConfig(pageSize = 6)
        ) {
            localWeatherDataSource.getHourlyWeatherPaged(dayWeatherId)
        }.flow
    }
}
