package com.example.perfectweatherallyear.ui.detailWeather

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perfectweatherallyear.api.ConnectionDetector
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.repository.LocationRepository
import com.example.perfectweatherallyear.repository.WeatherRepository
import com.example.perfectweatherallyear.ui.weekWeather.DAYS_NUMBER
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class DetailWeatherViewModel(val weatherRepository: WeatherRepository, val locationRepository: LocationRepository, context: Context) : ViewModel() {
    private val mConnectionDetector: ConnectionDetector = ConnectionDetector(context)
    private val compositeDisposable = CompositeDisposable()
    val localHourlyWeatherLiveData = MutableLiveData<List<HourWeather>>()

    fun loadHourlyWeather(dayWeather: DayWeather) {
        if (mConnectionDetector.isConnectingToInternet()) {
            compositeDisposable.add(locationRepository.getCityNameByCityId(dayWeather.cityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<String>() {
                    override fun onNext(name: String) {
                        getUpdatedHourlyWeather(DAYS_NUMBER, dayWeather, name)
                    }

                    override fun onError(e: Throwable) {
                        Log.i("RemoteHourWeatherLog", "couldn't get remote hourly weather")
                    }

                    override fun onComplete() {
                        Log.i("RemoteHourWeatherLog", "Got remote hourly weather")
                    }
                }))
        } else {
            getLocalHourWeather(dayWeather)
        }
    }

    fun getUpdatedHourlyWeather(numDay: Int, dayWeather: DayWeather, cityName: String) {
        compositeDisposable.add(weatherRepository.getRemoteHourlyWeather(numDay, dayWeather, cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<HourWeather>>() {
                override fun onNext(_listHourWeather: List<HourWeather>) {
                    weatherRepository.insertHourWeather(_listHourWeather)
                }

                override fun onError(e: Throwable) {
                    Log.i("UpdateWeatherLog", "Couldn't update weather")
                }

                override fun onComplete() {
                    getLocalHourWeather(dayWeather)
                }
            }))
    }

    fun getLocalHourWeather(dayWeather: DayWeather) : MutableLiveData<List<HourWeather>> {
        compositeDisposable.add(weatherRepository.getLocalHourWeather(dayWeather)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { listHourlyDayWeather ->
                localHourlyWeatherLiveData.value = listHourlyDayWeather
            })
        return localHourlyWeatherLiveData
    }

    fun clear() {
        weatherRepository.clear()
        compositeDisposable.clear()
    }
}