package com.example.perfectweatherallyear.ui.detailWeather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.perfectweatherallyear.R
import com.example.perfectweatherallyear.model.DayWeather

import com.google.gson.GsonBuilder

const val ARG_DAY_WEATHER: String = "DAY_WEATHER"

class DetailWeatherFragment : Fragment() {
    private lateinit var temperatureTextView: TextView
    private lateinit var precipitationTextView: TextView
    private lateinit var windTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        temperatureTextView = view.findViewById(R.id.temperatureTextView)
        precipitationTextView = view.findViewById(R.id.precipitationTextView)
        windTextView = view.findViewById(R.id.windTextView)

        val builder = GsonBuilder()
        val gson = builder.create()
        val dayWeather: DayWeather = gson.fromJson(arguments?.getString(ARG_DAY_WEATHER), DayWeather::class.java)

        temperatureTextView.text = dayWeather.temperature
        precipitationTextView.text = dayWeather.precipitation.toString()
        windTextView.text = dayWeather.wind.toString()
    }
}