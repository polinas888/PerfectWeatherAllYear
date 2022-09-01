package com.example.perfectweatherallyear.ui.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.LocationRepository
import kotlinx.coroutines.launch

class LocationViewModel(val locationRepository: LocationRepository) : ViewModel() {
    val listLocationsLiveData = MutableLiveData<List<Location>>()

    fun loadLocations() {
        viewModelScope.launch {
            val loadUserLocations = locationRepository.loadUserLocations()
            listLocationsLiveData.value = loadUserLocations
        }
    }
}