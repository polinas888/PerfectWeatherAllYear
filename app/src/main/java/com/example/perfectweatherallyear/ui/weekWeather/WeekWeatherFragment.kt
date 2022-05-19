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
import com.example.perfectweatherallyear.databinding.FragmentWeekWeatherBinding
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.ui.detailWeather.ARG_DAY_WEATHER
import com.example.perfectweatherallyear.ui.detailWeather.DetailWeatherFragment
import com.example.perfectweatherallyear.ui.weekWeather.WeatherForecastAdapter.OnItemClick
import com.example.perfectweatherallyear.util.NotificationUtil
import com.google.gson.GsonBuilder
import javax.inject.Inject

class WeekWeatherFragment : Fragment() {
    private var weekWeatherList: List<DayWeather> = listOf()
    private lateinit var weatherForecastAdapter: WeatherForecastAdapter
    private lateinit var binding: FragmentWeekWeatherBinding
    private val listener: OnItemClick = OnItemClickImpl()

    @Inject
    lateinit var weekWeatherViewModelFactory: WeekWeatherViewModelFactory

    private val weekWeatherViewModel by viewModels<WeekWeatherViewModel>{
        weekWeatherViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeekWeatherBinding.inflate(layoutInflater)
        requireContext().appComponent.inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weekWeatherViewModel.loadData()
        binding.apply {
            weatherForecastAdapter = WeatherForecastAdapter(weekWeatherList, listener)
            weatherForecastRecyclerView.adapter = weatherForecastAdapter
            weatherForecastRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        weekWeatherViewModel.weekWeatherLiveData.observe(viewLifecycleOwner) {
            weatherForecastAdapter.setData(it)
        }

        NotificationUtil.displayNotification(requireContext())
    }

    fun navigateToFragment(dayWeather: DayWeather){
        val fragment = DetailWeatherFragment()

        val args = Bundle()
        val builder = GsonBuilder()
        val gson = builder.create()
        val result: String = gson.toJson(dayWeather)

        args.putString(ARG_DAY_WEATHER, result)
        fragment.changeFragment(args, parentFragmentManager)
    }
}
