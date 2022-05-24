package com.example.perfectweatherallyear.di

import com.example.perfectweatherallyear.repository.localData.DatabaseFactory
import com.example.perfectweatherallyear.repository.localData.LocationDao
import com.example.perfectweatherallyear.repository.localData.WeatherDao
import dagger.Module
import dagger.Provides

@Module
object ModuleDatabase {
    @Provides
    fun provideWeatherDao(): WeatherDao {
        return DatabaseFactory.get().database.weatherDao()
    }

    @Provides
    fun provideLocationDao(): LocationDao {
        return DatabaseFactory.get().database.locationDao()
    }
}