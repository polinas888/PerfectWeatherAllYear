package com.example.perfectweatherallyear.di

import android.content.Context
import com.example.perfectweatherallyear.repository.LocationRepository
import com.example.perfectweatherallyear.repository.LocationRepositoryImpl
import com.example.perfectweatherallyear.repository.WeatherRepository
import com.example.perfectweatherallyear.repository.WeatherRepositoryImp
import com.example.perfectweatherallyear.repository.localData.LocalLocationDataSource
import com.example.perfectweatherallyear.repository.localData.LocalWeatherDataSource
import com.example.perfectweatherallyear.repository.localData.LocationDao
import com.example.perfectweatherallyear.repository.localData.WeatherDao
import com.example.perfectweatherallyear.repository.remoteData.weatherData.ForecastApiComDataSource
import com.example.perfectweatherallyear.repository.remoteData.weatherData.RemoteWeatherDataSource
import com.example.perfectweatherallyear.repository.remoteData.weatherData.WeatherApiCom
import dagger.Module
import dagger.Provides

@Module
object ApiRepositoryFactory {

    @Provides
    fun provideWeatherRepository(remoteWeatherDataSource: RemoteWeatherDataSource, localWeatherDataSource: LocalWeatherDataSource, context: Context)
    : WeatherRepository {
        return WeatherRepositoryImp(remoteDataSource = remoteWeatherDataSource, localWeatherDataSource = localWeatherDataSource, context = context)
    }

    @Provides
    fun provideLocationRepository(localLocationDataSource: LocalLocationDataSource): LocationRepository {
        return LocationRepositoryImpl(localLocationDataSource = localLocationDataSource)
    }

    @Provides
    fun provideRemoteWeatherDataSource(weatherApiCom: WeatherApiCom, weatherDao: WeatherDao, locationDao: LocationDao) : RemoteWeatherDataSource {
        return ForecastApiComDataSource(weatherApiCom, weatherDao, locationDao)
    }

    @Provides
    fun provideLocalWeatherDataSource(weatherDao: WeatherDao) : LocalWeatherDataSource {
        return LocalWeatherDataSource(weatherDao)
    }

    @Provides
    fun provideLocalLocationDataSource(locationDao: LocationDao) : LocalLocationDataSource {
        return LocalLocationDataSource(locationDao)
    }
}
