package com.example.perfectweatherallyear.ui.weekWeather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perfectweatherallyear.databinding.FragmentWeekWeatherBinding
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.Result
import com.example.perfectweatherallyear.ui.detailWeather.ARG_DAY_WEATHER
import com.example.perfectweatherallyear.ui.detailWeather.DetailWeatherFragment
import com.google.gson.GsonBuilder

class WeekWeatherFragment : Fragment(){
    private var _binding: FragmentWeekWeatherBinding? = null // why like this?
    private val binding get() = _binding!!
    private var weekWeatherMap:Map<String, DayWeather> = mapOf()

    private val weekWeatherViewModel by viewModels<WeekWeatherViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeekWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        binding.apply {
            weatherForecastRecyclerView.adapter = WeatherForecastAdapter(weekWeatherMap) { dayWeather -> adapterOnClick(dayWeather)}
            weatherForecastRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun adapterOnClick(dayWeather: DayWeather) {
        val fragment = DetailWeatherFragment()

        val args = Bundle()
        val builder = GsonBuilder()
        val gson = builder.create()

        val result: String = gson.toJson(dayWeather)

        args.putString(ARG_DAY_WEATHER, result)
        fragment.arguments = args

        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.replace(com.example.perfectweatherallyear.R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun loadData() {
        when(val weekWeather = weekWeatherViewModel.getWeekWeather()) {
            is Result.Error -> weekWeather.error
            is Result.Ok -> {
                val weekWeatherList = weekWeather.response
                val map: Map<String, DayWeather> = weekWeatherViewModel.combineListsIntoOrderedMap(
                    weekWeatherViewModel.weekDaysList, weekWeatherList)
                    weekWeatherMap = map
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}