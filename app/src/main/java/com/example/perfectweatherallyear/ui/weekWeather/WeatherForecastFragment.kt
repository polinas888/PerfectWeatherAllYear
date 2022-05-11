package com.example.perfectweatherallyear.ui.weekWeather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perfectweatherallyear.appComponent
import com.example.perfectweatherallyear.changeFragment
import com.example.perfectweatherallyear.databinding.FragmentWeatherForecastBinding
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.ui.detailWeather.ARG_DAY_WEATHER
import com.example.perfectweatherallyear.ui.detailWeather.DetailWeatherFragment
import com.example.perfectweatherallyear.util.NotificationUtil
import com.google.gson.GsonBuilder
import javax.inject.Inject

const val ARG_LOCATION: String = "LOCATION"
class WeatherForecastFragment : Fragment() {
    private lateinit var weatherForecastAdapter: WeatherForecastAdapter
    private lateinit var binding: FragmentWeatherForecastBinding
    lateinit var location: Location

    @Inject
    lateinit var weatherForecastViewModelFactory: WeatherForecastViewModelFactory

    private val weekWeatherViewModel by viewModels<WeatherForecastViewModel>{
        weatherForecastViewModelFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWeatherForecastBinding.inflate(layoutInflater)
        requireContext().appComponent.inject(this)

        val builder = GsonBuilder()
        val gson = builder.create()
        location = gson.fromJson(arguments?.getString(ARG_LOCATION), Location::class.java)

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
        NotificationUtil.displayNotification(requireContext())
    }

    private fun initViewModel(){
        weekWeatherViewModel.loadForecast(location)
        weekWeatherViewModel.weatherForecastLiveData.observe(viewLifecycleOwner) { weatherForecastAdapter.setData(it) }
    }

    private fun adapterOnClick(dayWeather: DayWeather) {
        val fragment = DetailWeatherFragment()
        val args = Bundle()
        val builder = GsonBuilder()
        val gson = builder.create()
        val result: String = gson.toJson(dayWeather)

        args.putString(ARG_DAY_WEATHER, result)
        fragment.changeFragment(args, parentFragmentManager)
    }
}
