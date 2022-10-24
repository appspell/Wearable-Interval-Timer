package com.appspell.sportintervaltimer.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 2,
    entities = [SavedInterval::class],
    autoMigrations = [
        AutoMigration(from = 1, to = 2) // TODO manual migration?
    ]
)
abstract class IntervalTimerDatabase : RoomDatabase() {
    abstract fun savedIntervalDao(): SavedIntervalDao
}