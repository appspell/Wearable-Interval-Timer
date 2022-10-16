package com.appspell.sportintervaltimer.timersetup

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val DEFAULT_SETS = 4
private const val DEFAULT_SECONDS = 60

class TimeSetupRepository @Inject constructor() {

    fun observeSavedInterval(): Flow<TimerSetupDataState> = flow {
        emit(
            TimerSetupDataState(
                sets = 1,
                workSeconds = 1,
                restSeconds = 1,
            )
        )
    }

    fun saveInterval(data: TimerSetupDataState) {
        // TODO
    }

    companion object {
        val DEFAULT_STATE = TimerSetupDataState(
            sets = DEFAULT_SETS,
            workSeconds = DEFAULT_SECONDS,
            restSeconds = DEFAULT_SECONDS,
        )
    }
}