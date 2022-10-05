package com.example.perfectweatherallyear.ui.weekWeather

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject

class WeatherFragmentFactory @Inject constructor() : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            WeatherForecastFragment::class.java.name -> WeatherForecastFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}
