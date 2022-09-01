package com.example.perfectweatherallyear.ui.location

import com.example.perfectweatherallyear.MainDispatcherRule
import com.example.perfectweatherallyear.getOrAwaitValue
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.FakeLocationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LocationViewModelTest {
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var locationRepository: FakeLocationRepository

//    @get:Rule
//    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

//    @get:Rule
//    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setupViewModel() {
        locationRepository = FakeLocationRepository()
        locationViewModel = LocationViewModel(locationRepository)
    }

    @Test
    fun loadLocations_listLocationsLiveDataValueEqualsListInRepository() = runTest {
        locationViewModel.loadLocations()
        val locations = locationViewModel.listLocationsLiveData.getOrAwaitValue()
        MatcherAssert.assertThat(
            locations, IsEqual(
                mutableListOf(
                    Location(id = 1, name = "Moscow"),
                    Location(id = 2, name = "London"),
                    Location(id = 3, "New-York")
                )
            )
        )
    }
}