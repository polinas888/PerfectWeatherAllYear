package com.example.perfectweatherallyear.ui.detailWeather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.perfectweatherallyear.databinding.FragmentDetailWeatherBinding
import com.example.perfectweatherallyear.model.DayWeather
import com.google.gson.GsonBuilder

const val ARG_DAY_WEATHER: String = "DAY_WEATHER"

class DetailWeatherFragment : Fragment() {
    private var _binding: FragmentDetailWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailWeatherBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val builder = GsonBuilder()
        val gson = builder.create()
        val dayWeather: DayWeather = gson.fromJson(arguments?.getString(ARG_DAY_WEATHER), DayWeather::class.java)

        binding.temperatureTextView.text = dayWeather.temperature
        binding.precipitationTextView.text = dayWeather.precipitation.toString()
        binding.windTextView.text = dayWeather.wind.toString()
    }
}