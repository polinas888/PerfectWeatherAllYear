package com.example.perfectweatherallyear.ui.detailWeather

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.perfectweatherallyear.model.DayWeather
import com.google.gson.GsonBuilder

class DetailWeatherViewModel: ViewModel() {
    fun getDetailWeather(args: Bundle): DayWeather {
        val builder = GsonBuilder()
        val gson = builder.create()
        return gson.fromJson(args.getString(ARG_DAY_WEATHER), DayWeather::class.java)
    }
}