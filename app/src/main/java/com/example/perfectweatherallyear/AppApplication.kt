package com.example.perfectweatherallyear

import android.app.Application
import android.content.Context
import com.example.perfectweatherallyear.di.AppComponent
import com.example.perfectweatherallyear.di.DaggerAppComponent
import com.example.perfectweatherallyear.repository.localData.DatabaseFactory

class AppApplication: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
        DatabaseFactory.initialize(this)
    }
}

val Context.appComponent: AppComponent
get() = when(this) {
    is AppApplication -> appComponent
    else -> this.applicationContext.appComponent
}