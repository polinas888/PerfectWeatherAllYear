package com.example.perfectweatherallyear.ui.weekWeather

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perfectweatherallyear.api.ConnectionDetector
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.LocationRepository
import com.example.perfectweatherallyear.repository.WeatherRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

const val DAYS_NUMBER = 3

class WeatherForecastViewModel(
    val weatherRepository: WeatherRepository,
    val locationRepository: LocationRepository,
    val context: Context
) : ViewModel() {
    private val mConnectionDetector: ConnectionDetector = ConnectionDetector(context)
    private val compositeDisposable = CompositeDisposable()
    val localWeatherForecastLiveData = MutableLiveData<List<DayWeather>>()

    fun loadForecast(location: Location) {
        if (mConnectionDetector.isConnectingToInternet()) {
            try {
                getUpdatedForecastWeather(location, DAYS_NUMBER)
            } catch (e: Exception) {
                Log.i("WeatherLog", "Couldn't get online weather")
            }
        } else {
            getLocalWeatherForecast(location, DAYS_NUMBER)
        }
    }

    private fun getUpdatedForecastWeather(location: Location, daysAmount: Int) {
        compositeDisposable.add(
            weatherRepository.getRemoteWeatherForecast(location.name, daysAmount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<DayWeather>>() {
                    override fun onNext(_listDayWeather: List<DayWeather>) {
                        weatherRepository.insertDayWeather(_listDayWeather)
                    }

                    override fun onError(e: Throwable) {
                        Log.i("UpdateWeatherLog", "Couldn't update weather")
                    }

                    override fun onComplete() {
                        getLocalWeatherForecast(location, DAYS_NUMBER)
                    }
                })
        )
    }

    fun getLocalWeatherForecast(location: Location, numDays: Int) : MutableLiveData<List<DayWeather>> {
        compositeDisposable.add(weatherRepository.getLocalWeatherForecast(location, numDays)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { listDayWeather ->
                localWeatherForecastLiveData.value = listDayWeather
            })
        return localWeatherForecastLiveData
    }

    fun clear() {
        weatherRepository.clear()
    }
}