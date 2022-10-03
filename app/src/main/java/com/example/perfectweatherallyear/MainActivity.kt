package com.example.perfectweatherallyear

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.perfectweatherallyear.databinding.ActivityMainBinding
import com.example.perfectweatherallyear.util.ForecastNotifierWorker
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    val logger = LoggerFactoryImpl().createLogger(LoggerType.TimberLogger)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAnalytics = Firebase.analytics
        logger.i("My log")
    }

    override fun onStop() {
        super.onStop()
        ForecastNotifierWorker.createWorker(this)
        }
}