package com.example.perfectweatherallyear.ui.location

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.LocationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class LocationViewModel(val locationRepository: LocationRepository) : ViewModel() {
    val listLocationsLiveData = MutableLiveData<List<Location>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.add(locationRepository.loadUserLocations()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<Location>>() {
                override fun onNext(listLocations: List<Location>) {
                    listLocationsLiveData.value = listLocations
                }

                override fun onError(e: Throwable) {
                    Log.i("LocationLog", "Couldn't get list of locations")
                }

                override fun onComplete() {
                    Log.i("LocationLog", "Got list of locations")
                }
            })
        )
    }

    fun clear() {
        compositeDisposable.clear()
    }
}
