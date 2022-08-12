package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.localData.LocalLocationDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val localLocationDataSource: LocalLocationDataSource
) : LocationRepository {
    private val mockLocations: List<Location> = listOf(
        Location(name = "Moscow"), Location(name = "London"),
        Location(name = "New-York")
    )
    private val compositeDisposable = CompositeDisposable()
    private var locationId = 0
    private var cityName = ""

    override fun insertLocations(locations: List<Location>) {
        compositeDisposable.add(
            Completable.fromAction {
                localLocationDataSource.insertLocations(locations)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    override fun getLocationIdByCityName(city: String): Int {
        compositeDisposable.add(localLocationDataSource.getLocationIdByCityName(city)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { id ->
                locationId = id
            })
        return locationId
    }

    override fun getCityNameByCityId(cityId: Int): String {
        compositeDisposable.add(localLocationDataSource.getCityNameByCityId(cityId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { name ->
                cityName = name
            })
        return cityName
    }

    override fun loadUserLocations(): Observable<List<Location>?> {
        insertLocations(mockLocations)
        return localLocationDataSource.getLocations()
    }
}