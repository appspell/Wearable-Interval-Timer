package com.appspell.sportintervaltimer

import android.app.Application
import androidx.startup.AppInitializer
import dagger.hilt.android.HiltAndroidApp
import net.danlew.android.joda.JodaTimeInitializer

@HiltAndroidApp
class IntervalTimerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppInitializer.getInstance(this).initializeComponent(JodaTimeInitializer::class.java)
    }
}