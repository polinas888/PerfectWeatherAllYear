package com.example.perfectweatherallyear.authorization.utils

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.perfectweatherallyear.authorization.R
import com.example.perfectweatherallyear.authorization.TAG

class BiometrPrompt {
    companion object {
        fun createPromptInfo(context: Context): BiometricPrompt.PromptInfo {
            return BiometricPrompt.PromptInfo.Builder()
                .setTitle(context.getString(R.string.prompt_info_title))
                .setSubtitle(context.getString(R.string.prompt_info_subtitle))
                .setDescription(context.getString(R.string.prompt_info_description))
                .setConfirmationRequired(false)
                .setNegativeButtonText(context.getString(R.string.prompt_info_use_app_password))
                .build()
        }

        fun createBiometricPrompt(fragment: Fragment): BiometricPrompt {
            val executor = ContextCompat.getMainExecutor(fragment.requireContext())

            val callback = object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.d(TAG, "$errorCode :: $errString")
                    if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                        //TODO   loginWithPassword()
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Log.d(TAG, "Authentication failed for an unknown reason")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Log.d(TAG, "Authentication was successful")
                    Navigation.navigateAuthorizationToLocation(fragment)
                }
            }

            return BiometricPrompt(fragment, executor, callback)
        }
    }
}