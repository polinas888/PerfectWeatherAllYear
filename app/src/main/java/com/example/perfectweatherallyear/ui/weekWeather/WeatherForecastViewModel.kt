package com.example.perfectweatherallyear.ui.weekWeather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.LocationRepository
import com.example.perfectweatherallyear.repository.WeatherRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

const val DAYS_NUMBER = 3

class WeatherForecastViewModel(
    val weatherRepository: WeatherRepository,
    val locationRepository: LocationRepository
) : ViewModel() {
    val weatherForecastLiveData = MutableLiveData<List<DayWeather>>()

    fun loadForecast(location: Location) {

        getWeatherForecast(location, DAYS_NUMBER)
            .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer { listWeather: List<DayWeather> ->  weatherForecastLiveData.value = listWeather}
            ) { e: Throwable -> Log.i("ErrorLog", e.toString()) }
        Log.i("Subscribe", "Subscribed")
    }

    private fun getWeatherForecast(location: Location, daysAmount: Int): Flowable<List<DayWeather>> {
        return weatherRepository.getWeatherForecast(location, daysAmount)
    }
}

