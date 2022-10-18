package com.appspell.sportintervaltimer.timer

import com.appspell.sportintervaltimer.db.SavedInterval
import com.appspell.sportintervaltimer.db.SavedIntervalDao
import com.appspell.sportintervaltimer.timer.TimerType.PREPARE
import com.appspell.sportintervaltimer.timer.TimerType.REST
import com.appspell.sportintervaltimer.timer.TimerType.UNDEFINED
import com.appspell.sportintervaltimer.timer.TimerType.WORK
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

// TODO replace it when implement a list
private const val SAVED_DEFAULT_NAME = "Default"

private const val PREPARE_TIMER_SECONDS = 15

class TimerRepository @Inject constructor(
    private val intervalsDao: SavedIntervalDao
) {

    private val _dataState =
        MutableStateFlow<TimerDataState>(DEFAULT_STATE)
    val dataState = _dataState.asSharedFlow()

    private val calendar = Calendar.getInstance()

    fun reset() {
        val savedData = intervalsDao.fetchByName(SAVED_DEFAULT_NAME)
        _dataState.value = savedData?.toDataState() ?: DEFAULT_STATE
    }

    suspend fun resume() {
        flow {
            while (true) {
                emit(_dataState.value)
                delay(1000)
            }
        }
            .map { state ->
//                state.updateState()
                state.copy(
                    timeLeftSeconds = state.timeLeftSeconds - 1
                )
            }
            .collect { state ->
                _dataState.value = state
            }

    }

    private fun TimerDataState.updateState(): TimerDataState {
        return if (this.timeLeftSeconds <= 0) {
            // time is out
            if (this.currentIteration > 0) {
                val nextType = when (this.currentType) {
                    PREPARE -> WORK
                    WORK -> REST
                    REST -> WORK
                    UNDEFINED -> PREPARE
                }
                val currentSet = if (nextType == WORK) {
                    this.currentSet - 1
                } else {
                    this.currentSet
                }
                val currentRoundEndMillis = this.getEndTimeMillis()
                val timeLeftSeconds =
                    ((currentRoundEndMillis - calendar.timeInMillis) / 1000).toInt()

                this.copy(
                    currentIteration = this.currentIteration - 1,
                    currentRoundEndMillis = currentRoundEndMillis,
                    timeLeftSeconds = timeLeftSeconds,
                    currentType = nextType,
                    currentSet = currentSet,
                    currentProgress = 0.0f
                )
            } else {
                this // TODO EXIT
            }
        } else {
            // update timer
            val currentRoundEndMillis = this.getEndTimeMillis()
            val timeLeftSeconds =
                ((currentRoundEndMillis - calendar.timeInMillis) / 1000).toInt()
            val progress = timeLeftSeconds.toFloat() / this.getRoundTimeSeconds()
            this.copy(
                timeLeftSeconds = timeLeftSeconds,
                currentProgress = progress
            )
        }
    }

    fun pause() {
        // TODO stop job
    }

    private fun TimerDataState.getRoundTimeSeconds(): Int {
        return when (this.currentType) {
            PREPARE -> this.prepareSeconds
            WORK -> this.workSeconds
            REST -> this.restSeconds
            else -> 0
        }
    }

    private fun TimerDataState.getEndTimeMillis(): Long {
        val currentTime = calendar.timeInMillis
        val roundTime = this.getRoundTimeSeconds().let { TimeUnit.SECONDS.toMillis(it.toLong()) }
        return currentTime + roundTime
    }

    private fun SavedInterval.toDataState() =
        TimerDataState(
            maxSets = this.sets,
            prepareSeconds = PREPARE_TIMER_SECONDS,
            workSeconds = this.workSeconds,
            restSeconds = this.restSeconds,

            currentIteration = this.sets * 2,
            timeLeftSeconds = 0,
            currentRoundEndMillis = 0,
            currentType = UNDEFINED,
            currentSet = this.sets,
            currentProgress = 0.0f
        )

    companion object {
        val DEFAULT_STATE = TimerDataState(
            currentSet = 0,
            maxSets = 0,
            currentType = UNDEFINED,
            prepareSeconds = PREPARE_TIMER_SECONDS,
            currentRoundEndMillis = 0,
            workSeconds = 0,
            restSeconds = 0,
            currentIteration = 0,
            currentProgress = 0.0f,
            timeLeftSeconds = 0
        )
    }
}