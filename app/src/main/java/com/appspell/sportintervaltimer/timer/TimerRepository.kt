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
import org.joda.time.DateTime
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

    @Volatile
    private var isPaused = false

    fun reset() {
        val savedData = intervalsDao.fetchByName(SAVED_DEFAULT_NAME)
        _dataState.value = savedData?.toDataState() ?: DEFAULT_STATE
    }

    suspend fun resume() {
        isPaused = false

        flow {
            while (!isPaused) {
                emit(
                    _dataState.value.copy(
                        isPaused = false
                    )
                )
                delay(16)
            }
        }
            .map { state ->
                val currentTime = DateTime.now().millis
                val timeLeftMillis = state.currentRoundEndMillis - currentTime
                val progress = timeLeftMillis.toFloat() /
                        TimeUnit.SECONDS.toMillis(
                            state.getRoundTimeSeconds(state.currentType).toLong()
                        )

                if (timeLeftMillis <= 0) {
                    // Round time is out
                    if (state.currentIteration == 0) {
                        // no more iterations
                        // TODO exit logic
                        pause()
                        return@map state
                    }
                    // Set up new round
                    val nextType = when (state.currentType) {
                        PREPARE -> WORK
                        WORK -> REST
                        REST -> WORK
                        UNDEFINED -> PREPARE
                    }
                    val currentSet =
                        if (nextType == REST) state.currentSet - 1 else state.currentSet

                    val nextRoundTimeSeconds = state.getRoundTimeSeconds(nextType)
                    val nextTimeEndMillis =
                        currentTime + TimeUnit.SECONDS.toMillis(nextRoundTimeSeconds.toLong())

                    state.copy(
                        currentType = nextType,
                        currentIteration = state.currentIteration - 1,
                        currentSet = currentSet,
                        timeLeftSeconds = nextRoundTimeSeconds,
                        currentRoundEndMillis = nextTimeEndMillis,
                        currentProgress = 0.0f
                    )
                } else {
                    // Continue with current rounds
                    state.copy(
                        timeLeftSeconds = TimeUnit.MILLISECONDS.toSeconds(timeLeftMillis).toInt(),
                        currentProgress = 1.0f - progress
                    )
                }

            }
            .collect { state ->
                _dataState.value = state
            }
    }

    fun pause() {
        isPaused = true
        _dataState.value = _dataState.value.copy(
            isPaused = true
        )
    }

    fun skipAndPause() {
        isPaused = true
        _dataState.value = _dataState.value.copy(
            isPaused = true,
            timeLeftSeconds = 0,
            currentRoundEndMillis = 0
        )
    }

    private fun TimerDataState.getRoundTimeSeconds(type: TimerType): Int {
        return when (type) {
            PREPARE -> this.prepareSeconds
            WORK -> this.workSeconds
            REST -> this.restSeconds
            else -> 0
        }
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
            currentProgress = 0.0f,
            isPaused = false
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
            timeLeftSeconds = 0,
            isPaused = false
        )
    }
}