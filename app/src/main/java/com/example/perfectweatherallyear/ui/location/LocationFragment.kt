package com.example.perfectweatherallyear.ui.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perfectweatherallyear.appComponent
import com.example.perfectweatherallyear.databinding.FragmentLocationBinding
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.util.EspressoIdlingResources
import javax.inject.Inject

class LocationFragment: Fragment() {
    private lateinit var binding: FragmentLocationBinding
    private lateinit var locationAdapter: LocationAdapter
    lateinit var locationViewModel: LocationViewModel

    @Inject
    lateinit var locationViewModelFactory: LocationViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLocationBinding.inflate(layoutInflater)
        requireContext().appComponent.inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationViewModel = ViewModelProvider(this, locationViewModelFactory)
            .get(LocationViewModel::class.java)

        binding.apply {
            locationAdapter = LocationAdapter { location -> adapterOnClick(location) }
            locationsRecyclerView.adapter = locationAdapter
            locationsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
        initViewModel()
        loadData()
    }

    fun initViewModel(){
        locationViewModel.listLocationsLiveData.observe(viewLifecycleOwner) { locationAdapter.setData(it) }
    }

    fun loadData() {
        EspressoIdlingResources.increment()
        locationViewModel.loadLocations()
        EspressoIdlingResources.decrement()
    }


    private fun adapterOnClick(location: Location) {
        val action = LocationFragmentDirections.actionLocationFragmentToWeekWeatherFragment(id = location.id, cityName = location.name)
        view?.findNavController()?.navigate(action)
    }
}

