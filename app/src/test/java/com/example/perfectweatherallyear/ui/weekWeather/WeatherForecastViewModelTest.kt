package com.example.perfectweatherallyear.ui.weekWeather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.perfectweatherallyear.FakeLocationRepository
import com.example.perfectweatherallyear.FakeWeatherRepository
import com.example.perfectweatherallyear.getOrAwaitValue
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.GeneralWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherForecastViewModelTest {
    private lateinit var weatherViewModel: WeatherForecastViewModel
    private lateinit var fakeWeatherRepository: FakeWeatherRepository
    private lateinit var locationRepository: FakeLocationRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupViewModel() = runTest {
        locationRepository = FakeLocationRepository()
        val locations = mutableListOf(Location(1, "Moscow"), Location(2, "London"))
        locationRepository.insertLocations(locations)

        val remoteForecastWeather = mutableListOf(
            DayWeather(
                3, "13-01-2000", 1,
                GeneralWeather("+15", "+20", "4", "10")
            ),
            DayWeather(
                4, "14-01-2000", 2,
                GeneralWeather("+18", "+26", "5", "12")
            )
        )

        fakeWeatherRepository = FakeWeatherRepository(remoteForecastWeather)
        val localForecastWeather = mutableListOf(
            DayWeather(
                1, "10-01-2000", 1,
                GeneralWeather("+7", "+15", "3", "11")
            ),
            DayWeather(
                2, "11-01-2000", 2,
                GeneralWeather("+8", "+16", "4", "12")
            )
        )
        fakeWeatherRepository.insertDayWeather(localForecastWeather)

        weatherViewModel = WeatherForecastViewModel(fakeWeatherRepository, locationRepository)
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun loadForecastWithInternet_returnUpdatedListLocations() = runTest {
        weatherViewModel.loadForecast(Location(1, "Moscow"))
        val forecast = weatherViewModel.weatherForecastLiveData.getOrAwaitValue()
        MatcherAssert.assertThat(forecast, IsEqual(listOf(
            DayWeather(
                1, "10-01-2000", 1,
                GeneralWeather("+7", "+15", "3", "11")
            ), DayWeather(
                3, "13-01-2000", 1,
                GeneralWeather("+15", "+20", "4", "10")
            )
        )))
    }

    @Test
    fun loadForecastWithNoInternet_returnNotUpdatedListLocations() = runTest {
        fakeWeatherRepository.connectionDetector.setOrTurnOffConnection(false)
        weatherViewModel.loadForecast(Location(1, "Moscow"))
        val forecast = weatherViewModel.weatherForecastLiveData.getOrAwaitValue()
        MatcherAssert.assertThat(forecast, IsEqual(listOf(
            DayWeather(
                1, "10-01-2000", 1,
                GeneralWeather("+7", "+15", "3", "11")
            )
        )))
    }
}