package com.example.perfectweatherallyear.ui.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.perfectweatherallyear.repository.LocationRepository

class LocationViewModelFactory constructor(val repository: LocationRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationViewModel::class.java))
            return LocationViewModel(repository) as T
        else
            throw IllegalArgumentException("Unable to construct viewModel")
    }
}
