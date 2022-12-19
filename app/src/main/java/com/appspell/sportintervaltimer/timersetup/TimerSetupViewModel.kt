package com.appspell.sportintervaltimer.timersetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appspell.sportintervaltimer.Navigation
import com.appspell.sportintervaltimer.utils.toTimeString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val MIN_SECONDS = 5
private const val STEP_SECONDS = 5

@HiltViewModel
class TimerSetupViewModel @Inject constructor(
    private val repository: TimeSetupRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TimeSetupRepository.DEFAULT_STATE.toUIState())
    val uiState: StateFlow<TimerSetupUiState> = _uiState

    private val _navigation =
        MutableSharedFlow<Navigation>(extraBufferCapacity = 1)
    val navigation = _navigation.asSharedFlow()

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
            sets = dataState.sets + 1,
        ).updateTotalTimeSeconds()
    }

    fun onSetsRemove() {
        dataState = dataState.copy(
            sets = if (dataState.sets > 1) {
                dataState.sets - 1
            } else {
                0
            },
        ).updateTotalTimeSeconds()
    }

    fun onWorkAdd() {
        dataState = dataState.copy(
            workSeconds = dataState.workSeconds + STEP_SECONDS,
        ).updateTotalTimeSeconds()
    }

    fun onWorkRemove() {
        dataState = dataState.copy(
            workSeconds = if (dataState.workSeconds >= MIN_SECONDS * 2) {
                dataState.workSeconds - STEP_SECONDS
            } else {
                MIN_SECONDS
            },
        ).updateTotalTimeSeconds()
    }

    fun onRestAdd() {
        dataState = dataState.copy(
            restSeconds = dataState.restSeconds + STEP_SECONDS,
        ).updateTotalTimeSeconds()
    }

    fun onRestRemove() {
        dataState = dataState.copy(
            restSeconds = if (dataState.restSeconds >= MIN_SECONDS * 2) {
                dataState.restSeconds - STEP_SECONDS
            } else {
                MIN_SECONDS
            }
        ).updateTotalTimeSeconds()
    }

    fun onSave() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                repository.saveInterval(dataState)
            }
            _navigation.emit(Navigation.Timer)
        }
    }

    private fun updateUiState() {
        _uiState.value = dataState.toUIState()
    }

    private fun TimerSetupDataState.toUIState() =
        TimerSetupUiState(
            sets = sets.toString(),
            work = workSeconds.toTimeString(),
            rest = restSeconds.toTimeString(),
            totalTimeSeconds = totalTimeSeconds
        )

    private fun TimerSetupDataState.updateTotalTimeSeconds() =
        this.copy(
            totalTimeSeconds = calculateTotalTime(
                this.sets,
                this.workSeconds,
                this.restSeconds
            )
        )
}