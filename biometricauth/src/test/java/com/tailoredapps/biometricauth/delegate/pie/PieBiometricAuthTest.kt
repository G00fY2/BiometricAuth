package com.tailoredapps.biometricauth.delegate.pie

import android.content.Context
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import io.mockk.*
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.After
import org.junit.Before
import org.junit.Test

class PieBiometricAuthTest {

    private lateinit var pieBiometricAuth: PieBiometricAuth

    private lateinit var context: Context

    private lateinit var fingerprintManagerCompat: FingerprintManagerCompat

    @Before
    fun setUp() {
        context = mockk(relaxed = true)
        fingerprintManagerCompat = mockk()

        mockkStatic(FingerprintManagerCompat::class)

        every { FingerprintManagerCompat.from(any()) } returns fingerprintManagerCompat

        pieBiometricAuth = spyk(PieBiometricAuth(context))
    }

    @After
    fun tearDown() {
        unmockkStatic(FingerprintManagerCompat::class)
    }


    @Test
    fun hasFingerprintHardwareTestTrue() {
        every { fingerprintManagerCompat.isHardwareDetected } returns true

        pieBiometricAuth.hasFingerprintHardware.shouldBeTrue()

        verify { fingerprintManagerCompat.isHardwareDetected }
    }

    @Test
    fun hasFingerprintHardwareTestFalse() {
        every { fingerprintManagerCompat.isHardwareDetected } returns false

        pieBiometricAuth.hasFingerprintHardware.shouldBeFalse()

        verify { fingerprintManagerCompat.isHardwareDetected }
    }


    @Test
    fun hasFingerprintsEnrolledTestTrue() {
        every { fingerprintManagerCompat.hasEnrolledFingerprints() } returns true

        pieBiometricAuth.hasFingerprintsEnrolled.shouldBeTrue()

        verify { fingerprintManagerCompat.hasEnrolledFingerprints() }
    }

    @Test
    fun hasFingerprintsEnrolledTestFalse() {
        every { fingerprintManagerCompat.hasEnrolledFingerprints() } returns false

        pieBiometricAuth.hasFingerprintsEnrolled.shouldBeFalse()

        verify { fingerprintManagerCompat.hasEnrolledFingerprints() }
    }


//    @Test
//    fun authenticateTestSuccess() {
//
//        val testObserver = pieBiometricAuth.authenticate(
//                "title", "subtitle", "description", "prompt",
//                "notRecognizedText", "negativeButtonText"
//        ).test()
//
//        verifyOrder {
//
//        }
//    }
}