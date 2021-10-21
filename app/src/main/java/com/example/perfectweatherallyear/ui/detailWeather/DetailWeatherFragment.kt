package com.example.perfectweatherallyear.ui.detailWeather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.perfectweatherallyear.databinding.FragmentDetailWeatherBinding
import com.example.perfectweatherallyear.model.DayWeather
import com.google.gson.GsonBuilder

const val ARG_DAY_WEATHER: String = "DAY_WEATHER"

class DetailWeatherFragment : Fragment() {
    private var _binding: FragmentDetailWeatherBinding? = null
    private val binding get() = _binding!!
    private val detailWeatherViewModel by viewModels<DetailWeatherViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val builder = GsonBuilder()
        val gson = builder.create()
        detailWeatherViewModel.setDayWeatherData(
            gson.fromJson(arguments?.getString(ARG_DAY_WEATHER), DayWeather::class.java)
        )

        binding.apply {
            temperatureTextView.text = detailWeatherViewModel.getDayWeatherData()?.temperature
            precipitationTextView.text = detailWeatherViewModel.getDayWeatherData()?.precipitation.toString()
            windTextView.text = detailWeatherViewModel.getDayWeatherData()?.wind.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}