package com.example.perfectweatherallyear.ui.weekWeather

import com.example.perfectweatherallyear.model.DayWeather

class OnItemClickImpl: WeatherForecastAdapter.OnItemClick {
    val fragment = WeekWeatherFragment()

    override fun onItemClick(dayWeather: DayWeather) {
        fragment.navigateToFragment(dayWeather)
    }
}