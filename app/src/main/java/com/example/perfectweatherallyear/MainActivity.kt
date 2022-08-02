package com.example.perfectweatherallyear

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.perfectweatherallyear.databinding.ActivityMainBinding
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.util.ForecastNotifierWorker
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase

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

        handleDynamicLinkFromFirebase()
        handleLinkFromNotification()
    }

    private fun handleLinkFromNotification() {
        val link = intent.getStringExtra("link")
        if (link != null) {
            val uri = Uri.parse(link)
            navigateToUriDestination(uri)
        }
    }

    private fun handleDynamicLinkFromFirebase() {
        Firebase.dynamicLinks.getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData: PendingDynamicLinkData? ->

                pendingDynamicLinkData?.link?.run(::navigateToUriDestination)
            }
            .addOnFailureListener(this) { e -> Log.i("DynamicLink", "getDynamicLink:onFailure", e) }
    }

    private fun navigateToUriDestination(uri: Uri) {
        val city = uri.getQueryParameter(CITY_NAME_PARAM)
        val id = uri.getQueryParameter(CITY_ID_PARAM)?.toInt()

        if (id != null && city != null) {
            val location = Location(id, city)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navHostFragment.navController.navigate(
                MobileNavigationDirections.actionGlobalWeekWeatherFragment(
                    location
                )
            )
        }
    }

    override fun onStop() {
        super.onStop()
        ForecastNotifierWorker.createWorker(this)
    }
}