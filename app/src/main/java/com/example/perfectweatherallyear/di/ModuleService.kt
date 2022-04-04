package com.example.perfectweatherallyear.di

import com.example.perfectweatherallyear.api.ApiRetrofitFactory
import com.example.perfectweatherallyear.repository.remoteData.weatherData.WeatherApiCom
import dagger.Module
import dagger.Provides

@Module
object ModuleService {
    @Provides
    fun provideWeatherApi(): WeatherApiCom {
        return ApiRetrofitFactory.weatherApiRetrofit().create(WeatherApiCom::class.java)
    }
}
