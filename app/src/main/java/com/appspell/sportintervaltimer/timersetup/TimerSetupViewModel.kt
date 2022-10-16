package com.appspell.sportintervaltimer.timersetup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TimerSetupViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<TimerSetupUIState>(DEFAULT_STATE.toUIState())
    val uiState: StateFlow<TimerSetupUIState> = _uiState

    private var dataState = DEFAULT_STATE

    private fun updateUiState() {
        _uiState.value = dataState.toUIState()
    }

    private fun TimerSetupDataState.toUIState() =
        TimerSetupUIState(
            sets = sets.toString(),
            work = work.toString(),
            rest = work.toString()
        )

    private companion object {
        val DEFAULT_STATE = TimerSetupDataState(
            sets = 4,
            work = 0,
            rest = 0,
        )
    }
}