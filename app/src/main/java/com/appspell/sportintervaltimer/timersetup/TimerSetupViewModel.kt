package com.appspell.sportintervaltimer.timersetup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

private const val DEFAULT_SETS = 4
private const val DEFAULT_SECONDS = 60

private const val MIN_SECONDS = 5
private const val STEP_SECONDS = 5

private const val TIME_SEPARATOR = " : "

@HiltViewModel
class TimerSetupViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<TimerSetupUIState>(DEFAULT_STATE.toUIState())
    val uiState: StateFlow<TimerSetupUIState> = _uiState

    private var dataState = DEFAULT_STATE

    fun onSetsAdd() {
        dataState = dataState.copy(
            sets = dataState.sets + 1
        )
        updateUiState()
    }

    fun onSetsRemove() {
        dataState = dataState.copy(
            sets = if (dataState.sets > 1) {
                dataState.sets - 1
            } else {
                0
            }
        )
        updateUiState()
    }

    fun onWorkAdd() {
        dataState = dataState.copy(
            workSeconds = dataState.workSeconds + STEP_SECONDS
        )
        updateUiState()
    }

    fun onWorkRemove() {
        dataState = dataState.copy(
            workSeconds = if (dataState.workSeconds >= MIN_SECONDS * 2) {
                dataState.workSeconds - STEP_SECONDS
            } else {
                MIN_SECONDS
            }
        )
        updateUiState()
    }

    fun onRestAdd() {
        dataState = dataState.copy(
            restSeconds = dataState.restSeconds + STEP_SECONDS
        )
        updateUiState()
    }

    fun onRestRemove() {
        dataState = dataState.copy(
            restSeconds = if (dataState.restSeconds >= MIN_SECONDS * 2) {
                dataState.restSeconds - STEP_SECONDS
            } else {
                MIN_SECONDS
            }
        )
        updateUiState()
    }

    private fun updateUiState() {
        _uiState.value = dataState.toUIState()
    }

    private fun TimerSetupDataState.toUIState() =
        TimerSetupUIState(
            sets = sets.toString(),
            work = workSeconds.toTimeString(),
            rest = restSeconds.toTimeString()
        )

    private fun Int.toTimeString(): String {
        val min = this / 60
        val second = this - min * 60
        return "${min.showTwoDigits()}$TIME_SEPARATOR${second.showTwoDigits()}"
    }

    private fun Int.showTwoDigits(): String =
        if (this < 10) "0$this" else this.toString()

    private companion object {
        val DEFAULT_STATE = TimerSetupDataState(
            sets = DEFAULT_SETS,
            workSeconds = DEFAULT_SECONDS,
            restSeconds = DEFAULT_SECONDS,
        )
    }
}