package com.appspell.sportintervaltimer.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [SavedInterval::class])
abstract class IntervalTimerDatabase : RoomDatabase() {
    abstract fun savedIntervalDao(): SavedIntervalDao
}