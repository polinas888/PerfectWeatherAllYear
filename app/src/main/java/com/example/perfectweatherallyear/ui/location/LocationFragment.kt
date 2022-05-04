package com.example.perfectweatherallyear.ui.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perfectweatherallyear.appComponent
import com.example.perfectweatherallyear.changeFragment
import com.example.perfectweatherallyear.databinding.FragmentLocationBinding
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.ui.weekWeather.ARG_LOCATION
import com.example.perfectweatherallyear.ui.weekWeather.WeatherForecastFragment
import com.google.gson.GsonBuilder
import javax.inject.Inject

class LocationFragment: Fragment() {
    private val locations: List<Location> = listOf(Location(name = "Moscow"), Location(name = "London"),
        Location(name = "New-York"))
    private lateinit var binding: FragmentLocationBinding
    private lateinit var locationAdapter: LocationAdapter

    @Inject
    lateinit var locationViewModelFactory: LocationViewModelFactory

    private val locationViewModel by viewModels<LocationViewModel>{
        locationViewModelFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLocationBinding.inflate(layoutInflater)
        requireContext().appComponent.inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            locationAdapter = LocationAdapter(locations) { location -> adapterOnClick(location) }
            locationsRecyclerView.adapter = locationAdapter
            locationsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
        locationViewModel.loadLocationsToDB(locations)
    }

    private fun adapterOnClick(location: Location) {
        val fragment = WeatherForecastFragment()

        val args = Bundle()
        val builder = GsonBuilder()
        val gson = builder.create()
        val result: String = gson.toJson(location)

        args.putString(ARG_LOCATION, result)
        fragment.changeFragment(args, parentFragmentManager)
    }
}

