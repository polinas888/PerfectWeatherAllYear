package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.localData.LocalWeatherDataSource
import com.example.perfectweatherallyear.repository.remoteData.weatherData.RemoteWeatherDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteWeatherDataSource,
    private val localWeatherDataSource: LocalWeatherDataSource
) : WeatherRepository {
    private val compositeDisposable = CompositeDisposable()

    override fun insertDayWeather(dataWeatherData: List<DayWeather>?) {
        compositeDisposable.add(Completable.fromAction {
            localWeatherDataSource.insertDayWeather(dataWeatherData)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }

    override fun getRemoteWeatherForecast(city: String, daysAmount: Int): Observable<List<DayWeather>> {
        return remoteDataSource.getWeatherForecast(city, daysAmount)
    }

    override fun getLocalWeatherForecast(location: Location, numDays: Int) : Observable<List<DayWeather>> {
        return localWeatherDataSource.getWeatherForecast(location.id, numDays)
    }

    override fun insertHourWeather(hourWeatherData: List<HourWeather>) {
        compositeDisposable.add(
            Completable.fromAction {
                localWeatherDataSource.insertHourlyWeather(hourWeatherData)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    override fun getRemoteHourlyWeather(numDay: Int, dayWeather: DayWeather, cityName: String): Observable<List<HourWeather>> {
        return remoteDataSource.getHourlyWeather(numDay, dayWeather, cityName)
    }

    override fun getLocalHourWeather(dayWeather: DayWeather) : Observable<List<HourWeather>> {
        return localWeatherDataSource.getHourlyWeather(dayWeather.id)
    }

    override fun clear() {
        compositeDisposable.clear()
    }
}
