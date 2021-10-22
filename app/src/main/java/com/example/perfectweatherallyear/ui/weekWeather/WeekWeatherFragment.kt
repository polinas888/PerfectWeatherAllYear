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
import com.example.perfectweatherallyear.ui.detailWeather.ARG_DAY_WEATHER
import com.example.perfectweatherallyear.ui.detailWeather.DetailWeatherFragment
import com.google.gson.GsonBuilder

class WeekWeatherFragment : Fragment(){
    private var _binding: FragmentWeekWeatherBinding? = null // why like this?
    private val binding get() = _binding!!

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
        binding.apply {
            weatherForecastRecyclerView.adapter = WeatherForecastAdapter(weekWeatherViewModel.weekWeatherMap)  {dayWeather -> adapterOnClick(dayWeather)}
            weatherForecastRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun adapterOnClick(dayWeather: DayWeather) {
        val fragment = DetailWeatherFragment()

        val args = Bundle()
        val builder = GsonBuilder()
        val gson = builder.create()

        val result: String = gson.toJson(weekWeatherViewModel.getCurrentDayWeather(position))

        args.putString(ARG_DAY_WEATHER, result)
        fragment.arguments = args

        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.replace(com.example.perfectweatherallyear.R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}