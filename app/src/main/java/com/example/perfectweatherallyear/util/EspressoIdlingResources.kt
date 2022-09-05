package com.example.perfectweatherallyear.util

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResources {
    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}

inline fun <T> wrapEspressoIdlingResource(function: () -> T): T {
    EspressoIdlingResources.increment()
    return try {
        function()
    } finally {
        EspressoIdlingResources.decrement()
    }
}