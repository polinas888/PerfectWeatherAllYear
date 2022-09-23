package com.example.perfectweatherallyear.ui.weekWeather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perfectweatherallyear.appComponent
import com.example.perfectweatherallyear.databinding.FragmentWeatherForecastBinding
import com.example.perfectweatherallyear.model.DayWeather
import javax.inject.Inject

class WeatherForecastFragment : Fragment() {
    private lateinit var weatherForecastAdapter: WeatherForecastAdapter
    private lateinit var binding: FragmentWeatherForecastBinding
    private val args by navArgs<WeatherForecastFragmentArgs>()

    @Inject
    lateinit var weatherForecastViewModelFactory: WeatherForecastViewModelFactory

    private val weekWeatherViewModel by viewModels<WeatherForecastViewModel>{
        weatherForecastViewModelFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWeatherForecastBinding.inflate(layoutInflater)
        requireContext().appComponent.inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            weatherForecastAdapter = WeatherForecastAdapter{ dayWeather -> adapterOnClick(dayWeather) }
            weatherForecastRecyclerView.adapter = weatherForecastAdapter
            weatherForecastRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
        initViewModel()

        binding.swipeRefresh.setOnRefreshListener {
            weekWeatherViewModel.loadForecast(args.location)
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun initViewModel(){
        weekWeatherViewModel.loadForecast(args.location)
        weekWeatherViewModel.weatherForecastLiveData.observe(viewLifecycleOwner) { weatherForecastAdapter.setData(it) }
    }

    private fun adapterOnClick(dayWeather: DayWeather) {
        findNavController().navigate(WeatherForecastFragmentDirections.actionWeekWeatherFragmentToDetailWeatherFragment(dayWeather))
    }
}
