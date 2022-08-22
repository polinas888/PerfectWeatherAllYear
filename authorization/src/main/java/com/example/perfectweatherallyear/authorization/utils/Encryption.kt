package com.example.perfectweatherallyear.authorization.utils

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

const val KEY_NAME = "default_key"
const val ANDROID_KEY_STORE = "AndroidKeyStore"
class Encryption {

    companion object {
        private lateinit var keyStore: KeyStore
        private lateinit var keyGenerator: KeyGenerator

        fun setupKeyStoreAndKeyGenerator() {
            try {
                keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
            } catch (e: KeyStoreException) {
                throw RuntimeException("Failed to get an instance of KeyStore", e)
            }

            try {
                keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    ANDROID_KEY_STORE
                )
            } catch (e: Exception) {
                when (e) {
                    is NoSuchAlgorithmException,
                    is NoSuchProviderException ->
                        throw RuntimeException("Failed to get an instance of KeyGenerator", e)
                    else -> throw e
                }
            }
        }

        fun setupCiphers(): Cipher {
            val cipher: Cipher
            try {
                val cipherString = "${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_CBC}/${KeyProperties.ENCRYPTION_PADDING_PKCS7}"
                cipher = Cipher.getInstance(cipherString)
            } catch (e: Exception) {
                when (e) {
                    is NoSuchAlgorithmException,
                    is NoSuchPaddingException ->
                        throw RuntimeException("Failed to get an instance of Cipher", e)
                    else -> throw e
                }
            }
            return cipher
        }

        fun createKey() {
            try {
                keyStore.load(null)

                val keyProperties = KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                val builder = KeyGenParameterSpec.Builder(KEY_NAME, keyProperties)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .setInvalidatedByBiometricEnrollment(true)

                keyGenerator.run {
                    init(builder.build())
                    generateKey()
                }
            } catch (e: Exception) {
                when (e) {
                    is NoSuchAlgorithmException,
                    is InvalidAlgorithmParameterException,
                    is CertificateException,
                    is IOException -> throw RuntimeException(e)
                    else -> throw e
                }
            }
        }

        fun initCipher(cipher: Cipher, keyName: String): Boolean {
            try {
                keyStore.load(null)
                cipher.init(Cipher.ENCRYPT_MODE, keyStore.getKey(keyName, null) as SecretKey)
                return true
            } catch (e: Exception) {
                when (e) {
                    is KeyPermanentlyInvalidatedException -> return false
                    is KeyStoreException,
                    is CertificateException,
                    is UnrecoverableKeyException,
                    is IOException,
                    is NoSuchAlgorithmException,
                    is InvalidKeyException -> throw RuntimeException("Failed to init Cipher", e)
                    else -> throw e
                }
            }
        }
    }
}