package com.example.perfectweatherallyear.ui.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perfectweatherallyear.appComponent
import com.example.perfectweatherallyear.databinding.FragmentLocationBinding
import com.example.perfectweatherallyear.model.Location
import com.google.gson.GsonBuilder
import javax.inject.Inject

class LocationFragment: Fragment() {
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
            locationAdapter = LocationAdapter { location -> adapterOnClick(location) }
            locationsRecyclerView.adapter = locationAdapter
            locationsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
        initViewModel()
    }

    private fun initViewModel(){
        locationViewModel.loadLocations()
        locationViewModel.listLocationsLiveData.observe(viewLifecycleOwner) { locationAdapter.setData(it) }
    }

    private fun adapterOnClick(location: Location) {
        val action = LocationFragmentDirections.actionLocationFragmentToWeekWeatherFragment(id = location.id, cityName = location.name)
        view?.findNavController()?.navigate(action)
    }
}

