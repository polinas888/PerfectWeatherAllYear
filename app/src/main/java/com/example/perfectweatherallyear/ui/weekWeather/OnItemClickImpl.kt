package com.example.perfectweatherallyear.ui.weekWeather

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.perfectweatherallyear.changeFragment
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.ui.detailWeather.ARG_DAY_WEATHER
import com.example.perfectweatherallyear.ui.detailWeather.DetailWeatherFragment
import com.google.gson.GsonBuilder

class OnItemClickImpl: WeatherForecastAdapter.OnItemClick {
    override fun onItemClick(dayWeather: DayWeather, parentFragmentManager: FragmentManager) {
        val fragment = DetailWeatherFragment()

        val args = Bundle()
        val builder = GsonBuilder()
        val gson = builder.create()
        val result: String = gson.toJson(dayWeather)

        args.putString(ARG_DAY_WEATHER, result)
        fragment.changeFragment(args, parentFragmentManager)
    }
}