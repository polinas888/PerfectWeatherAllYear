package com.example.perfectweatherallyear.ui.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.LocationRepository
import kotlinx.coroutines.launch

class LocationViewModel(val locationRepository: LocationRepository) : ViewModel() {

    fun loadLocationsToDB(locations: List<Location>) {
        viewModelScope.launch {
            locationRepository.insertLocations(locations)
        }
    }
}