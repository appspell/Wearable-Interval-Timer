package com.appspell.sportintervaltimer.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideChannelDao(appDatabase: IntervalTimerDatabase): SavedIntervalDao {
        return appDatabase.savedIntervalDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): IntervalTimerDatabase {
        return Room.databaseBuilder(
            appContext,
            IntervalTimerDatabase::class.java,
            "IntervalTimerDatabase"
        )
            .build()
    }
}