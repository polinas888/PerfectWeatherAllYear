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
    private val detailWeatherViewModel by viewModels<DetailWeatherViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailWeatherBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.detailViewModel = detailWeatherViewModel

        detailWeatherViewModel.temperature.observe(viewLifecycleOwner, {
            detailWeatherViewModel._temperature.postValue(it)
        })

        detailWeatherViewModel.precipitation.observe(viewLifecycleOwner, {
            detailWeatherViewModel._precipitation.postValue(it)
        })

        detailWeatherViewModel.wind.observe(viewLifecycleOwner, {
            detailWeatherViewModel._wind.postValue(it)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val builder = GsonBuilder()
        val gson = builder.create()
        detailWeatherViewModel.setDayWeatherData(
            gson.fromJson(arguments?.getString(ARG_DAY_WEATHER), DayWeather::class.java)
        )
        detailWeatherViewModel.load()
    }
}