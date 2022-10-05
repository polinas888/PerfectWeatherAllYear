package com.example.perfectweatherallyear.ui.weekWeather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.perfectweatherallyear.R
import com.example.perfectweatherallyear.atPosition
import com.example.perfectweatherallyear.fakes.FakeAndroidLocationRepository
import com.example.perfectweatherallyear.fakes.FakeAndroidWeatherRepository
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.util.EspressoIdlingResources
import com.example.perfectweatherallyear.utils.DataBindingIdlingResource
import com.example.perfectweatherallyear.utils.monitorFragment
import com.google.gson.GsonBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@LargeTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class WeatherForecastFragmentTest {
    private lateinit var weatherRepository: FakeAndroidWeatherRepository
    private lateinit var locationRepository: FakeAndroidLocationRepository
    private lateinit var mockWeatherViewModel: WeatherForecastViewModel
    private lateinit var dataBindingIdlingResource: DataBindingIdlingResource
    private lateinit var fragmentScenario: FragmentScenario<WeatherForecastFragment>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupForTest() = runTest {
        locationRepository = FakeAndroidLocationRepository()
        val locations = listOf(Location(1, "Moscow"), Location(2, "London"))
        locationRepository.insertLocations(locations)
        weatherRepository = FakeAndroidWeatherRepository()
        mockWeatherViewModel = WeatherForecastViewModel(weatherRepository, locationRepository)

        val location = Location(1, "Moscow")
        val bundle = WeatherForecastFragmentArgs(location.id, location.name).toBundle()
        fragmentScenario = launchFragmentInContainer(
            bundle, R.style.ThemeOverlay_AppCompat_Light
        )

        fragmentScenario.withFragment {
            weekWeatherViewModel = mockWeatherViewModel
            initViewModel()
        }
        dataBindingIdlingResource = DataBindingIdlingResource()
        dataBindingIdlingResource.monitorFragment(fragmentScenario)
        fragmentScenario.withFragment {
            loadData()
        }

        IdlingRegistry.getInstance().register(EspressoIdlingResources.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }


    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun loadListWeatherInRecyclerView_DataDisplayedInUi() = runTest {

        Espresso.onView(withId(R.id.weatherForecastRecyclerView))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.weatherForecastRecyclerView))
            .check(matches(atPosition(0, isDisplayed())))
    }

    @Test
    fun loadListWeatherInRecyclerView_CorrectDataDisplayedInUi() = runTest {

        Espresso.onView(withId(R.id.weatherForecastRecyclerView))
            .check(matches(atPosition(0, hasDescendant(withText("10-01-2000")))))
        Espresso.onView(withId(R.id.weatherForecastRecyclerView))
            .check(matches(atPosition(0, hasDescendant(withText("+7")))))
        Espresso.onView(withId(R.id.weatherForecastRecyclerView))
            .check(matches(atPosition(0, hasDescendant(withText("/" + "+15")))))
        Espresso.onView(withId(R.id.weatherForecastRecyclerView))
            .check(matches(atPosition(0, hasDescendant(withText("3")))))
        Espresso.onView(withId(R.id.weatherForecastRecyclerView))
            .check(matches(atPosition(0, hasDescendant(withText("11")))))
    }

    @Test
    fun clickDayWeather_navigateToDetailWeatherFragmentWithRightArg() = runTest {

        val listWeather = mockWeatherViewModel.weatherForecastLiveData.value

        val navController = Mockito.mock(NavController::class.java)
        fragmentScenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        Espresso.onView(withId(R.id.weatherForecastRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click())
        )

        val dayWeather = listWeather?.get(0)
        val builder = GsonBuilder()
        val gson = builder.create()
        val result: String = gson.toJson(dayWeather)
        Mockito.verify(navController).navigate(
            WeatherForecastFragmentDirections.actionWeekWeatherFragmentToDetailWeatherFragment(
                result
            )
        )
    }
}