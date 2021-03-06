package com.example.perfectweatherallyear.ui.detailWeather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perfectweatherallyear.appComponent
import com.example.perfectweatherallyear.databinding.FragmentDetailWeatherBinding
import com.example.perfectweatherallyear.model.DayWeather
import com.google.gson.GsonBuilder
import javax.inject.Inject

const val ARG_DAY_WEATHER: String = "DAY_WEATHER"
class DetailWeatherFragment : Fragment() {
    private lateinit var detailWeatherForecastAdapter: DetailWeatherForecastAdapter
    private lateinit var binding: FragmentDetailWeatherBinding
    lateinit var dayWeather: DayWeather

    @Inject
    lateinit var detailWeatherViewModelFactory: DetailWeatherViewModelFactory

    private val detailWeatherViewModel by viewModels<DetailWeatherViewModel>{
        detailWeatherViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailWeatherBinding.inflate(layoutInflater)
        requireContext().appComponent.inject(this)

        val gson = GsonBuilder().create()
        dayWeather = gson.fromJson(arguments?.getString(ARG_DAY_WEATHER), DayWeather::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            detailWeatherForecastAdapter = DetailWeatherForecastAdapter()
            hourWeatherRecyclerView.adapter = detailWeatherForecastAdapter
            hourWeatherRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
        initViewModel()

        binding.swipeRefresh.setOnRefreshListener {
            detailWeatherViewModel.loadData(dayWeather)
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun initViewModel(){
        detailWeatherViewModel.loadData(dayWeather)
        detailWeatherViewModel.detailWeatherLiveData.observe(viewLifecycleOwner) { detailWeatherForecastAdapter.setData(it) }
    }
}
