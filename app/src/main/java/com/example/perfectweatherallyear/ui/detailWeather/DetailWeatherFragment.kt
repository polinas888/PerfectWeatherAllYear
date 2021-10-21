package com.example.perfectweatherallyear.ui.detailWeather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.perfectweatherallyear.databinding.FragmentDetailWeatherBinding

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

        binding.apply {
            arguments?.let {
                temperatureTextView.text = detailWeatherViewModel.getDetailWeather(it).temperature
                precipitationTextView.text = detailWeatherViewModel.getDetailWeather(it).precipitation.toString()
                windTextView.text = detailWeatherViewModel.getDetailWeather(it).wind.toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}