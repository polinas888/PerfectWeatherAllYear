package com.example.perfectweatherallyear.di

import android.content.Context
import com.example.perfectweatherallyear.api.ConnectionDetectorImpl
import com.example.perfectweatherallyear.repository.LocationRepository
import com.example.perfectweatherallyear.repository.LocationRepositoryImpl
import com.example.perfectweatherallyear.repository.WeatherRepository
import com.example.perfectweatherallyear.repository.WeatherRepositoryImp
import com.example.perfectweatherallyear.repository.localData.*
import com.example.perfectweatherallyear.repository.remoteData.weatherData.ForecastApiComDataSource
import com.example.perfectweatherallyear.repository.remoteData.weatherData.RemoteWeatherDataSource
import com.example.perfectweatherallyear.repository.remoteData.weatherData.WeatherApiCom
import dagger.Module
import dagger.Provides

@Module
object ApiRepositoryFactory {

    @Provides
    fun provideWeatherRepository(remoteWeatherDataSource: RemoteWeatherDataSource, weatherDataSourceImpl: LocalWeatherDataSourceImpl, locationDataSourceImpl: LocalLocationDataSourceImpl, connectionDetector: ConnectionDetectorImpl)
    : WeatherRepository {
        return WeatherRepositoryImp(remoteDataSource = remoteWeatherDataSource, weatherDataSource = weatherDataSourceImpl, locationDataSource = locationDataSourceImpl, connectionDetector = connectionDetector)
    }

    @Provides
    fun provideLocationRepository(localLocationDataSourceImpl: LocalLocationDataSourceImpl): LocationRepository {
        return LocationRepositoryImpl(localLocationDataSource = localLocationDataSourceImpl)
    }

    @Provides
    fun provideRemoteWeatherDataSource(weatherApiCom: WeatherApiCom) : RemoteWeatherDataSource {
        return ForecastApiComDataSource(weatherApiCom)
    }

    @Provides
    fun provideLocalWeatherDataSource(weatherDao: WeatherDao) : LocalWeatherDataSourceImpl {
        return LocalWeatherDataSourceImpl(weatherDao)
    }

    @Provides
    fun provideLocalLocationDataSource(locationDao: LocationDao) : LocalLocationDataSourceImpl {
        return LocalLocationDataSourceImpl(locationDao)
    }

    @Provides
    fun provideConnectionDetector(context: Context) : ConnectionDetectorImpl {
        return ConnectionDetectorImpl(context)
    }
}
