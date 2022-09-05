package com.example.perfectweatherallyear.ui.location

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.testing.launchFragmentInContainer
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
import com.example.perfectweatherallyear.di.ApiRepositoryFactory
import com.example.perfectweatherallyear.di.ModuleDatabase
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.LocationRepository
import com.example.perfectweatherallyear.repository.localData.DatabaseFactory
import com.example.perfectweatherallyear.repository.localData.LocalLocationDataSourceImpl
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
    private lateinit var repository: LocationRepository
    private lateinit var locationViewModel: LocationViewModel
    private val dataBindingIdlingResource = DataBindingIdlingResource()
    private val lock = Any()

    @Before
    fun initRepository() {
        repository = ApiRepositoryFactory.provideLocationRepository(LocalLocationDataSourceImpl(ModuleDatabase.provideLocationDao()))
        locationViewModel = LocationViewModel(repository)
    }

    @After
    @VisibleForTesting
    fun cleanupDb() = runTest {
        synchronized(lock) {
            DatabaseFactory.get().database.apply {
                clearAllTables()
                close()
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
    fun initViewModel_loadLocations_DisplayedThemInUi() = runTest {
        locationViewModel.loadLocations()
        val fragmentScenario = launchFragmentInContainer<LocationFragment>(Bundle(), R.style.ThemeOverlay_AppCompat_Light)
        dataBindingIdlingResource.monitorFragment(fragmentScenario)

        onView(withId(R.id.locationsRecyclerView)).check(matches(atPosition(0, isDisplayed())))
        onView(withId(R.id.locationsRecyclerView))
            .check(matches(atPosition(0, hasDescendant(withText("Moscow")))))
        onView(withId(R.id.locationsRecyclerView)).check(matches(atPosition(1, isDisplayed())))
        onView(withId(R.id.locationsRecyclerView))
            .check(matches(atPosition(1, hasDescendant(withText("London")))));
        onView(withId(R.id.locationsRecyclerView)).check(matches(atPosition(2, isDisplayed())))
        onView(withId(R.id.locationsRecyclerView))
            .check(matches(atPosition(2, hasDescendant(withText("New-York")))));
    }

    @Test
    fun clickFirstLocation_navigateToWeatherForecastFragmentWithFirstLocationArg() = runTest {
        repository.insertLocations(listOf(Location(1, "Moscow"), Location(2, "London")))

        val scenario = launchFragmentInContainer<LocationFragment>(Bundle(), R.style.ThemeOverlay_AppCompat_Light)
        val navController = Mockito.mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.locationsRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText("Moscow")), click()
                ))

        Mockito.verify(navController).navigate(
            LocationFragmentDirections.actionLocationFragmentToWeekWeatherFragment(Location(1, "Moscow").toString())
        )
    }
}