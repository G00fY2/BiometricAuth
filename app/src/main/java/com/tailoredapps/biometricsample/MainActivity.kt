package com.tailoredapps.biometricsample

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tailoredapps.biometricauth.BiometricAuth
import com.tailoredapps.biometricauth.BiometricAuthenticationCancelledException
import com.tailoredapps.biometricauth.BiometricAuthenticationException
import io.reactivex.android.schedulers.AndroidSchedulers

class MainActivity : AppCompatActivity() {

    companion object {
        private const val LOG_TAG = "MainActivity"
    }

    private val biometricAuth: BiometricAuth by lazy { BiometricAuth.create(this) }

    private val button: Button by lazy { findViewById<Button>(R.id.button) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            testAuthenticate()
        }
    }

    private fun testAuthenticate() {
        if (biometricAuth.hasFingerprintHardware.not()) {
            Toast.makeText(this, "Devices provides no fingerprint hardware", Toast.LENGTH_SHORT).show()
        } else if (biometricAuth.hasFingerprintsEnrolled.not()) {
            Toast.makeText(this, "No fingerprints enrolled", Toast.LENGTH_SHORT).show()
        } else {
            biometricAuth.authenticate(
                    title = "Please authenticate",
                    subtitle = "Using 'Awesome Feature' requires your authentication.",
                    description = "'Awesome Feature' exposes data private to you, which is why you need to authenticate.",
                    negativeButtonText = "Cancel",
                    prompt = "Touch the fingerprint sensor",
                    notRecognizedErrorText = "Not recognized"
            )
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                                Log.d(LOG_TAG, "onSuccess()")
                            },
                            { throwable ->
                                when (throwable) {
                                    is BiometricAuthenticationException -> {
                                        Toast.makeText(this, "Error: ${throwable.errorString}", Toast.LENGTH_SHORT).show()
                                        Log.e(LOG_TAG, "BiometricAuthenticationException(${throwable.errorMessageId}, '${throwable.errorString}')", throwable)
                                    }
                                    is BiometricAuthenticationCancelledException -> {
                                        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
                                        Log.d(LOG_TAG, "onError(BiometricAuthenticationCancelledException)")
                                    }
                                    else -> Log.e(LOG_TAG, "onError()", throwable)
                                }
                            }
                    )
        }
    }
}
