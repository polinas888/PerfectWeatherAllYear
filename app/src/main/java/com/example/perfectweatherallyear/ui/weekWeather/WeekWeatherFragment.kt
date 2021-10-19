package com.example.perfectweatherallyear.ui.weekWeather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perfectweatherallyear.databinding.FragmentWeekWeatherBinding
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.ui.detailWeather.ARG_DAY_WEATHER
import com.example.perfectweatherallyear.ui.detailWeather.DetailWeatherFragment
import com.google.gson.GsonBuilder

class WeekWeatherFragment : Fragment(), WeatherForecastAdapter.ViewHolder.OnItemListener {
    private var _binding: FragmentWeekWeatherBinding? = null // why like this?
    private val binding get() = _binding!!

    private val weekWeatherMap = mapOf(
        "Monday" to DayWeather("+15/+5", 15, 5),
        "Tuesday" to DayWeather("+15/+5", 15, 5), "Wednesday" to DayWeather("+16/+6", 20, 10),
        "Thursday" to DayWeather("+20/+15", 70, 2), "Friday" to DayWeather("+15/+7", 10, 15),
        "Saturday" to DayWeather("+10/+5", 20, 5), "Sunday" to DayWeather("+7/+0", 15, 8)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeekWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            weatherForecastRecyclerView.adapter = WeatherForecastAdapter(weekWeatherMap, this@WeekWeatherFragment)
            weatherForecastRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onItemClick(position: Int) {
        val fragment = DetailWeatherFragment()

        val args = Bundle()
        val currentDayWeather = weekWeatherMap[weekWeatherMap.keys.toTypedArray()[position]]

        val builder = GsonBuilder()
        val gson = builder.create()
        val result: String = gson.toJson(currentDayWeather)
        args.putString(ARG_DAY_WEATHER, result)
        fragment.arguments = args

        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.add(com.example.perfectweatherallyear.R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}