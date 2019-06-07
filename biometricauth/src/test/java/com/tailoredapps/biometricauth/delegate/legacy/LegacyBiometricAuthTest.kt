package com.tailoredapps.biometricauth.delegate.legacy

import com.tailoredapps.biometricauth.BiometricAuth
import io.mockk.mockk
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import javax.crypto.Mac

class LegacyBiometricAuthTest {

    private lateinit var legacyBiometricAuth: LegacyBiometricAuth

    @Before
    fun setUp() {
        legacyBiometricAuth = LegacyBiometricAuth()
    }

    @Test
    fun hasFingerprintHardwareTest() {
        legacyBiometricAuth.hasFingerprintHardware.shouldBeFalse()
    }

    @Test
    fun hasFingerprintsEnrolled() {
        legacyBiometricAuth.hasFingerprintsEnrolled.shouldBeFalse()
    }

    @Test
    fun authenticateCompletableTest() {
        val testObserver = legacyBiometricAuth.authenticate(
                "title",
                "subtitle",
                "description",
                "prompt",
                "notRecognizedText",
                "negativeButtonText"
        ).test()

        testObserver.assertNotComplete()
        testObserver.assertNoValues()
        testObserver.errorCount() shouldEqualTo 1
        testObserver.assertError { throwable ->
            throwable is NotImplementedError && throwable.message == "Pre Android 23 devices do not support native biometric authentication"
        }
    }

    @Test
    fun authenticateSingleTest() {
        val testObserver = legacyBiometricAuth.authenticate(
                BiometricAuth.Crypto(mockk<Mac>()),
                "title",
                "subtitle",
                "description",
                "prompt",
                "notRecognizedText",
                "negativeButtonText"
        ).test()

        testObserver.assertNotComplete()
        testObserver.assertNoValues()
        testObserver.errorCount() shouldEqualTo 1
        testObserver.assertError { throwable ->
            throwable is NotImplementedError && throwable.message == "Pre Android 23 devices do not support native biometric authentication"
        }
    }
}