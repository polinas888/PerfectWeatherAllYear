package com.example.perfectweatherallyear.ui.weekWeather

import androidx.annotation.VisibleForTesting
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.perfectweatherallyear.R
import com.example.perfectweatherallyear.fakes.FakeAndroidLocationRepository
import com.example.perfectweatherallyear.fakes.FakeAndroidWeatherRepository
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.LocationRepository
import com.example.perfectweatherallyear.repository.WeatherRepository
import com.example.perfectweatherallyear.repository.localData.DatabaseFactory
import com.example.perfectweatherallyear.util.EspressoIdlingResources
import com.example.perfectweatherallyear.utils.DataBindingIdlingResource
import com.example.perfectweatherallyear.utils.monitorFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class WeatherForecastFragmentTest {
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var locationRepository: LocationRepository
    private lateinit var weatherViewModel: WeatherForecastViewModel
    private val dataBindingIdlingResource = DataBindingIdlingResource()
    private val lock = Any()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initRepository() = runTest {
        locationRepository = FakeAndroidLocationRepository()
        weatherRepository = FakeAndroidWeatherRepository()
        weatherViewModel = WeatherForecastViewModel(weatherRepository, locationRepository)
        cleanDb()
    }

    @After
    @VisibleForTesting
    fun cleanDb() = cleanupDb()

    private fun cleanupDb() = runTest {
        synchronized(lock) {
            DatabaseFactory.get().database.apply {
                clearAllTables()
            }
        }
    }

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResources.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun initViewModel_loadListWeather_DisplayedInUi() = runBlockingTest {
        val location = Location(1, "Moscow")

        val bundle = WeatherForecastFragmentArgs(location.id, location.name).toBundle()
        val fragmentScenario = launchFragmentInContainer<WeatherForecastFragment>(
            bundle, R.style.ThemeOverlay_AppCompat_Light
        )
        dataBindingIdlingResource.monitorFragment(fragmentScenario)

        Espresso.onView(ViewMatchers.withId(R.id.weatherForecastRecyclerView))
            .check(matches(isDisplayed()))
    }
}