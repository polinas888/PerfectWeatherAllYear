package com.example.perfectweatherallyear.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.Fragment
import com.example.perfectweatherallyear.authorization.databinding.FragmentAuthorizationBinding
import com.example.perfectweatherallyear.authorization.utils.BiometrPrompt
import com.example.perfectweatherallyear.authorization.utils.Encryption
import com.example.perfectweatherallyear.authorization.utils.KEY_NAME
import com.example.perfectweatherallyear.authorization.utils.toast
import javax.crypto.Cipher

const val TAG = "Authentication"
class AuthorizationFragment : Fragment() {
    private lateinit var binding: FragmentAuthorizationBinding
    private lateinit var biometricPrompt: BiometricPrompt

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAuthorizationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Encryption.setupKeyStoreAndKeyGenerator()
        val cipher = Encryption.setupCiphers()
        biometricPrompt = BiometrPrompt.createBiometricPrompt( this)
        loginPressed(cipher)
    }

    private fun loginPressed(cipher: Cipher) {
        val biometricManager = BiometricManager.from(requireContext())
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Encryption.createKey()
                binding.logButton.setOnClickListener(LoginButtonClickListener(cipher, KEY_NAME))
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                requireContext().toast("No hardware for biometric features")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                requireContext().toast("Biometric features are currently unavailable.")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                requireContext().toast("Please associate a biometric credential with your account.")
            else ->
                requireContext().toast("An unknown error occurred. Please check your Biometric settings")
        }
    }

    private inner class LoginButtonClickListener constructor(var cipher: Cipher, var keyName: String)
        : View.OnClickListener {

        override fun onClick(view: View) {
            val promptInfo = BiometrPrompt.createPromptInfo(requireContext())

            if (Encryption.initCipher(cipher, keyName)) {
                biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
            } else {
             //TODO   loginWithPassword()
            }
        }
    }
}