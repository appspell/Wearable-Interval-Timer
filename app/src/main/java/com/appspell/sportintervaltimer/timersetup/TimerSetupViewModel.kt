package com.appspell.sportintervaltimer.timersetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MIN_SECONDS = 5
private const val STEP_SECONDS = 5

private const val TIME_SEPARATOR = " : "

@HiltViewModel
class TimerSetupViewModel @Inject constructor(
    private val repository: TimeSetupRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<TimerSetupUIState>(TimeSetupRepository.DEFAULT_STATE.toUIState())
    val uiState: StateFlow<TimerSetupUIState> = _uiState

    private var dataState = TimeSetupRepository.DEFAULT_STATE
        set(value) {
            field = value
            updateUiState()
        }

    init {
        viewModelScope.launch {
            repository.observeSavedInterval()
                .flowOn(Dispatchers.Default)
                .collect { savedData ->
                    dataState = savedData
                }
        }
    }

    fun onSetsAdd() {
        dataState = dataState.copy(
            sets = dataState.sets + 1
        )
    }

    fun onSetsRemove() {
        dataState = dataState.copy(
            sets = if (dataState.sets > 1) {
                dataState.sets - 1
            } else {
                0
            }
        )
    }

    fun onWorkAdd() {
        dataState = dataState.copy(
            workSeconds = dataState.workSeconds + STEP_SECONDS
        )
    }

    fun onWorkRemove() {
        dataState = dataState.copy(
            workSeconds = if (dataState.workSeconds >= MIN_SECONDS * 2) {
                dataState.workSeconds - STEP_SECONDS
            } else {
                MIN_SECONDS
            }
        )
    }

    fun onRestAdd() {
        dataState = dataState.copy(
            restSeconds = dataState.restSeconds + STEP_SECONDS
        )
    }

    fun onRestRemove() {
        dataState = dataState.copy(
            restSeconds = if (dataState.restSeconds >= MIN_SECONDS * 2) {
                dataState.restSeconds - STEP_SECONDS
            } else {
                MIN_SECONDS
            }
        )
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

}