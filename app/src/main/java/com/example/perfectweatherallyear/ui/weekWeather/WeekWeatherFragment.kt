package com.example.perfectweatherallyear.ui.weekWeather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perfectweatherallyear.changeFragment
import com.example.perfectweatherallyear.databinding.FragmentWeekWeatherBinding
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.WeatherRepositoryImp
import com.example.perfectweatherallyear.repository.remoteData.WeatherData.ForecastApiComDataSource
import com.example.perfectweatherallyear.ui.WeekWeatherViewModelFactory
import com.example.perfectweatherallyear.ui.detailWeather.ARG_DAY_WEATHER
import com.example.perfectweatherallyear.ui.detailWeather.DetailWeatherFragment
import com.google.gson.GsonBuilder

class WeekWeatherFragment : Fragment() {
    private var weekWeatherList: List<DayWeather> = listOf()
    lateinit var weatherForecastAdapter: WeatherForecastAdapter
    private lateinit var binding: FragmentWeekWeatherBinding

    private val weekWeatherViewModel by viewModels<WeekWeatherViewModel>{
        WeekWeatherViewModelFactory(
            WeatherRepositoryImp(ForecastApiComDataSource())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeekWeatherBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weekWeatherViewModel.loadData()
        binding.apply {
            weatherForecastAdapter = WeatherForecastAdapter(weekWeatherList) { dayWeather -> adapterOnClick(dayWeather) }
            weatherForecastRecyclerView.adapter = weatherForecastAdapter
            weatherForecastRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        weekWeatherViewModel.weekWeatherLiveData.observe(viewLifecycleOwner, {
            weatherForecastAdapter.setData(it)
        })
    }

    private fun adapterOnClick(weather: DayWeather) {
        val fragment = DetailWeatherFragment()

        val args = Bundle()
        val builder = GsonBuilder()
        val gson = builder.create()
        val result: String = gson.toJson(weather)

        args.putString(ARG_DAY_WEATHER, result)
        fragment.changeFragment(args, parentFragmentManager)
    }
}
