package com.example.perfectweatherallyear.ui.location

import androidx.annotation.VisibleForTesting
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.perfectweatherallyear.FakeAndroidLocationRepository
import com.example.perfectweatherallyear.R
import com.example.perfectweatherallyear.atPosition
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.LocationRepository
import com.example.perfectweatherallyear.repository.localData.DatabaseFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class LocationFragmentTest {
    private lateinit var repository: LocationRepository
    private val lock = Any()

    @Before
    fun initRepository() {
        repository = FakeAndroidLocationRepository()
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

    @Test
    fun listLocations_DisplayedInUi() = runTest {
        val listLocations =
            listOf(Location(1, "Moscow"), Location(2, "London"))
        repository.insertLocations(listLocations)
        launchFragmentInContainer<LocationFragment>(null, R.style.ThemeOverlay_AppCompat_Light)

        onView(withId(R.id.locationsRecyclerView)).check(matches(atPosition(0, isDisplayed())))
        onView(withId(R.id.locationsRecyclerView))
            .check(matches(atPosition(0, hasDescendant(withText("Moscow")))))
        onView(withId(R.id.locationsRecyclerView)).check(matches(atPosition(1, isDisplayed())))
        onView(withId(R.id.locationsRecyclerView))
            .check(matches(atPosition(1, hasDescendant(withText("London")))));
    }
}