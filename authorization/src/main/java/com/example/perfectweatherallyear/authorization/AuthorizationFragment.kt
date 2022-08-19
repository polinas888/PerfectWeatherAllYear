package com.example.perfectweatherallyear.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.perfectweatherallyear.authorization.databinding.FragmentAuthorizationBinding


class AuthorizationFragment : Fragment() {
    private lateinit var binding: FragmentAuthorizationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAuthorizationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logButton.setOnClickListener {
            navigateAuthorizationToLocation()
        }
    }

    private fun navigateAuthorizationToLocation() {
        val deepLink = NavDeepLinkRequest.Builder
            .fromUri("perfectweatherallyear://location".toUri())
            .build()

        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.nav_authorization, true)
            .setEnterAnim(R.anim.nav_default_enter_anim)
            .setExitAnim(R.anim.nav_default_exit_anim)
            .build()

        findNavController().navigate(deepLink, navOptions)
    }
}