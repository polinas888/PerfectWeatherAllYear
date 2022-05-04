package com.example.perfectweatherallyear.di

import com.example.perfectweatherallyear.api.ApiRetrofitFactory
import com.example.perfectweatherallyear.repository.localData.DatabaseFactory
import com.example.perfectweatherallyear.repository.localData.LocationDao
import com.example.perfectweatherallyear.repository.localData.WeatherDao
import com.example.perfectweatherallyear.repository.remoteData.weatherData.WeatherApiCom
import dagger.Module
import dagger.Provides

@Module
object ModuleService {
    @Provides
    fun provideWeatherApi(): WeatherApiCom {
        return ApiRetrofitFactory.weatherApiRetrofit().create(WeatherApiCom::class.java)
    }

    @Provides
    fun provideWeatherDao(): WeatherDao {
        return DatabaseFactory.get().database.weatherDao()
    }

    @Provides
    fun provideLocationDao(): LocationDao {
        return DatabaseFactory.get().database.locationDao()
    }
}
