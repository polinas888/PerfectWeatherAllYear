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

const val CITY_ID_PARAM = "id"
const val CITY_NAME_PARAM = "city"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAnalytics = Firebase.analytics

        getDynamicLinkFromFirebase()
        openLinkFromNotification()
    }

    private fun openLinkFromNotification() {
        val link = intent.getStringExtra("link")
        if (link != null) {
            val uri = Uri.parse(link)
            getParamAndOpenDeepLink(uri)
        }
    }

    private fun getDynamicLinkFromFirebase() {
        Firebase.dynamicLinks.getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData: PendingDynamicLinkData? ->
                var deepLink: Uri? = null

                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link

                    if (deepLink != null) {
                        getParamAndOpenDeepLink(deepLink)
                    }
                }
            }
            .addOnFailureListener(this) {
                    e -> Log.i("DynamicLink", "getDynamicLink:onFailure", e) }
    }

    private fun getParamAndOpenDeepLink(uri: Uri) {
        val city = uri.getQueryParameter(CITY_NAME_PARAM)
        val id = uri.getQueryParameter(CITY_ID_PARAM)?.toInt()

        if (id != null && city != null) {
            val location = Location(id, city)
            addFragment(WeatherForecastFragment(), location)
        }
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