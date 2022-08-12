package com.example.perfectweatherallyear.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.localData.LocalLocationDataSource
import com.example.perfectweatherallyear.repository.localData.LocalWeatherDataSource
import com.example.perfectweatherallyear.repository.remoteData.weatherData.RemoteWeatherDataSource
import com.example.perfectweatherallyear.ui.weekWeather.DAYS_NUMBER
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteWeatherDataSource,
    private val localWeatherDataSource: LocalWeatherDataSource,
    private val localLocationDataSource: LocalLocationDataSource,
    context: Context
) : WeatherRepository {
    private val compositeDisposable = CompositeDisposable()
    private val remoteWeatherForecastLiveData = MutableLiveData<List<DayWeather>>()
    private val remoteHourlyWeatherLiveData = MutableLiveData<List<HourWeather>>()
    private val localWeatherForecastLiveData = MutableLiveData<List<DayWeather>>()
    private val localHourlyWeatherLiveData = MutableLiveData<List<HourWeather>>()
    private val listDayWeather = mutableListOf<DayWeather>()
    private val listHourWeather = mutableListOf<HourWeather>()
    private var cityName = ""

    private fun insertDayWeather(dataWeatherData: List<DayWeather>?) {
        compositeDisposable.add(Completable.fromAction {
            localWeatherDataSource.insertDayWeather(dataWeatherData)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }

    override fun updateForecastWeather(location: Location, daysAmount: Int) {
        compositeDisposable.add(
            remoteDataSource.getWeatherForecast(location.name, daysAmount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<DayWeather>>() {
                    override fun onNext(_listDayWeather: List<DayWeather>) {
                        insertDayWeather(listDayWeather)
                        listDayWeather.addAll(_listDayWeather)
                    }

                    override fun onError(e: Throwable) {
                        Log.i("UpdateWeatherLog", "Couldn't update weather")
                    }

                    override fun onComplete() {
                        remoteWeatherForecastLiveData.value = listDayWeather
                    }
                })
        )
    }

    override fun getLocalWeatherForecastLiveData(location: Location, numDays: Int) : MutableLiveData<List<DayWeather>> {
        compositeDisposable.add(localWeatherDataSource.getWeatherForecast(location.id, numDays)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { listDayWeather ->
                localWeatherForecastLiveData.value = listDayWeather
            })
        return localWeatherForecastLiveData
    }

    override fun getRemoteWeatherForecastLiveData() : MutableLiveData<List<DayWeather>> {
        return remoteWeatherForecastLiveData
    }

    override fun updateHourWeather(dayWeather: DayWeather) {
        compositeDisposable.add(localLocationDataSource.getCityNameByCityId(dayWeather.cityId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<String>() {
                override fun onNext(name: String) {
                    cityName = name
                    updateHourlyWeather(dayWeather)
                }

                override fun onError(e: Throwable) {
                    Log.i("RemoteHourWeatherLog", "couldn't get remote hourly weather")
                }

                override fun onComplete() {
                    Log.i("RemoteHourWeatherLog", "Got remote hourly weather")
                }
            }))
    }

    private fun updateHourlyWeather(dayWeather: DayWeather) {
        compositeDisposable.add( remoteDataSource.getHourlyWeather(DAYS_NUMBER, dayWeather, cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<HourWeather>>() {
                override fun onNext(_listHourWeather: List<HourWeather>) {
                    insertHourWeather()
                    listHourWeather.addAll(_listHourWeather)
                }

                override fun onError(e: Throwable) {
                    Log.i("UpdateWeatherLog", "Couldn't update weather")
                }

                override fun onComplete() {
                    remoteHourlyWeatherLiveData.value = listHourWeather
                }
            }))
    }

    private fun insertHourWeather() {
        compositeDisposable.add(
            Completable.fromAction {
                remoteHourlyWeatherLiveData.value?.let { listHourlyWeather ->
                    localWeatherDataSource.insertHourlyWeather(listHourlyWeather)
                }
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    override fun getRemoteHourWeatherLiveData(): MutableLiveData<List<HourWeather>> {
        return remoteHourlyWeatherLiveData
    }

    override fun getLocalHourWeatherLiveData(dayWeather: DayWeather) : MutableLiveData<List<HourWeather>> {
        compositeDisposable.add(localWeatherDataSource.getHourlyWeather(dayWeather.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { listHourlyDayWeather ->
                localHourlyWeatherLiveData.value = listHourlyDayWeather
            })
        return localHourlyWeatherLiveData
    }

    override fun clear() {
        compositeDisposable.clear()
    }
}
