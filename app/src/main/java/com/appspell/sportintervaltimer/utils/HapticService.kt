package com.appspell.sportintervaltimer.utils

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.VibrationEffect
import android.os.Vibrator
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

// TODO revisit usage of this class
class HapticService @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val vibrator = context.getSystemService(VIBRATOR_SERVICE) as Vibrator?

    private val shortEffect =
        VibrationEffect.createWaveform(longArrayOf(0, 150), VibrationEffect.DEFAULT_AMPLITUDE)

    private val longEffect =
        VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)

    fun shortVibration() {
        vibrator?.vibrate(shortEffect)
    }

    fun longVibration() {
        vibrator?.vibrate(longEffect)
    }
}