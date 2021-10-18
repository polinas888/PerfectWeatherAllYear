package com.example.perfectweatherallyear.ui.weekWeather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perfectweatherallyear.model.DayWeather
import androidx.fragment.app.FragmentTransaction
import com.example.perfectweatherallyear.databinding.FragmentWeekWeatherBinding
import com.example.perfectweatherallyear.ui.detailWeather.ARG_DAY_WEATHER
import com.example.perfectweatherallyear.ui.detailWeather.DetailWeatherFragment
import com.google.gson.GsonBuilder

class WeekWeatherFragment : Fragment(), WeatherForecastAdapter.ViewHolder.OnItemListener {
    private var _binding: FragmentWeekWeatherBinding? = null // why like this?
    private val binding get() = _binding!!

    private val dayOfWeekList = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    private val weatherPerWeekList = listOf(DayWeather("+15/+5", 15, 5),
        DayWeather("+15/+5", 15, 5), DayWeather("+16/+6", 20, 10),
            DayWeather("+20/+15", 70, 2), DayWeather("+15/+7", 10, 15),
            DayWeather("+10/+5", 20, 5), DayWeather("+7/+0", 15, 8)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeekWeatherBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.weatherForecastRecyclerView.adapter = WeatherForecastAdapter(dayOfWeekList, weatherPerWeekList, this)
        binding.weatherForecastRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onItemClick(position: Int) {
        val fragment = DetailWeatherFragment()

        val args = Bundle()
        val dayWeather = DayWeather(temperature = weatherPerWeekList[position].temperature,
            precipitation = weatherPerWeekList[position].precipitation, wind = weatherPerWeekList[position].wind)
        val builder = GsonBuilder()
        val gson = builder.create()
        val result: String = gson.toJson(dayWeather)
        args.putString(ARG_DAY_WEATHER, result)
        fragment.arguments = args

        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(com.example.perfectweatherallyear.R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null // обязательно?
    }
}