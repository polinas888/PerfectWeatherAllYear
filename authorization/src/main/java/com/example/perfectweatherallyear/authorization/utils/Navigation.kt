package com.example.perfectweatherallyear.authorization.utils

import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.perfectweatherallyear.authorization.R

class Navigation {
    companion object {
        fun navigateAuthorizationToLocation(fragment: Fragment) {
            val deepLink = NavDeepLinkRequest.Builder
                .fromUri("perfectweatherallyear://location".toUri())
                .build()

            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.nav_authorization, true)
                .setEnterAnim(R.anim.nav_default_enter_anim)
                .setExitAnim(R.anim.nav_default_exit_anim)
                .build()
            fragment.findNavController().navigate(deepLink, navOptions)
        }
    }
}