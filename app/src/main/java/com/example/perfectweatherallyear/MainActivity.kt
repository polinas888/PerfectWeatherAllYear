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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAnalytics = Firebase.analytics

//        binding.crashButton.setOnClickListener {
//            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
//                param(FirebaseAnalytics.Param.ITEM_ID, binding.crashButton.id.toString())
//                param(FirebaseAnalytics.Param.ITEM_NAME, "CrashButton")
//            }
//            Firebase.crashlytics.log("this is my runtime exception")
//            Firebase.crashlytics.setCustomKeys {
//                key("my_log", "this is my runtime exception")
//            }
            //throw RuntimeException("Test Crash") // Force a crash
//        }
    }

    override fun onStop() {
        super.onStop()
        ForecastNotifierWorker.createWorker(this)
        }
}