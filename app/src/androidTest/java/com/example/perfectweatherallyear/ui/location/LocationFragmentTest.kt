package com.example.perfectweatherallyear.ui.location

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.perfectweatherallyear.R
import com.example.perfectweatherallyear.atPosition
import com.example.perfectweatherallyear.fakes.FakeAndroidLocationRepository
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.util.EspressoIdlingResources
import com.example.perfectweatherallyear.utils.DataBindingIdlingResource
import com.example.perfectweatherallyear.utils.monitorFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@LargeTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class LocationFragmentTest {
    private lateinit var repository: FakeAndroidLocationRepository
    private lateinit var mockLocationViewModel: LocationViewModel
    private lateinit var dataBindingIdlingResource: DataBindingIdlingResource
    private lateinit var fragmentScenario: FragmentScenario<LocationFragment>

    @Before
    fun setUpForTest() = runTest {
        repository = FakeAndroidLocationRepository()
        val locations = listOf(Location(1, "New-York"), Location(2, "London"))
        repository.insertLocations(locations)
        mockLocationViewModel = LocationViewModel(repository)
        fragmentScenario = launchFragmentInContainer(
            Bundle(), R.style.ThemeOverlay_AppCompat_Light
        )

        fragmentScenario.withFragment {
            locationViewModel = mockLocationViewModel
            initViewModel()
        }

        dataBindingIdlingResource = DataBindingIdlingResource()
        dataBindingIdlingResource.monitorFragment(fragmentScenario)
        IdlingRegistry.getInstance().register(EspressoIdlingResources.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun loadLocations_DataDisplayedInUi() = runTest {

        fragmentScenario.withFragment {
            loadData()
        }

        onView(withId(R.id.locationsRecyclerView)).check(matches(atPosition(0, isDisplayed())))
        onView(withId(R.id.locationsRecyclerView)).check(matches(atPosition(1, isDisplayed())))
    }

    @Test
    fun loadLocations_CorrectDataDisplayedInUi() = runTest {

        fragmentScenario.withFragment {
            loadData()
        }

        onView(withId(R.id.locationsRecyclerView))
            .check(matches(atPosition(0, hasDescendant(withText("New-York")))))
        onView(withId(R.id.locationsRecyclerView))
            .check(matches(atPosition(1, hasDescendant(withText("London")))));
    }

    @Test
    fun clickFirstLocation_navigateToWeatherForecastFragmentWithFirstLocationArg() = runTest {

        fragmentScenario.withFragment {
            loadData()
        }

        val listLocations = mockLocationViewModel.listLocationsLiveData.value

        val navController = Mockito.mock(NavController::class.java)
        fragmentScenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.locationsRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));

        Mockito.verify(navController).navigate(
            LocationFragmentDirections.actionLocationFragmentToWeekWeatherFragment(listLocations?.get(0)!!.id, listLocations[0].name)
        )
    }
}