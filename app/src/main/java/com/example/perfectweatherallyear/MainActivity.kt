package com.example.perfectweatherallyear

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.perfectweatherallyear.databinding.ActivityMainBinding
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.ui.weekWeather.ARG_LOCATION
import com.example.perfectweatherallyear.ui.weekWeather.WeatherForecastFragment
import com.example.perfectweatherallyear.util.ForecastNotifierWorker
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAnalytics = Firebase.analytics

        getDynamicLinkFromFirebase()
    }

    private fun getDynamicLinkFromFirebase() {
        Firebase.dynamicLinks.getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData: PendingDynamicLinkData? ->
                var deepLink: Uri? = null

                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link

                        val city = deepLink?.getQueryParameter("city")
                        val id = deepLink?.getQueryParameter("id")?.toInt()

                    if (id != null && city != null) {
                        val location = Location(id, city)
                        addFragment(WeatherForecastFragment(), location)
                    }
                }
            }
            .addOnFailureListener(this) {
                    e -> Log.i("DynamicLink", "getDynamicLink:onFailure", e) }
    }

    override fun onStop() {
        super.onStop()
        ForecastNotifierWorker.createWorker(this)
        }

    private fun addFragment(fragment: Fragment, location: Location) {
        val args = Bundle()
        val builder = GsonBuilder()
        val gson = builder.create()
        val result: String = gson.toJson(location)

        args.putString(ARG_LOCATION, result)
        fragment.arguments = args
        supportFragmentManager.beginTransaction()
            .replace(binding.navHostFragment.id, fragment)
            .addToBackStack(null)
            .commit()
    }
}