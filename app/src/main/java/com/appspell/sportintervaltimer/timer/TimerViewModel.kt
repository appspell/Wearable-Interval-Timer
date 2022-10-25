package com.appspell.sportintervaltimer.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appspell.sportintervaltimer.Navigation
import com.appspell.sportintervaltimer.Navigation.Finish
import com.appspell.sportintervaltimer.utils.toTimeString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val repository: TimerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TimerRepository.DEFAULT_STATE.toUIState())
    val uiState: StateFlow<TimerUiState> = _uiState

    private val _navigation =
        MutableSharedFlow<Navigation>(extraBufferCapacity = 1)
    val navigation = _navigation.asSharedFlow()

    private var lastCoroutineTimerJob: Job? = null

    init {
        // TODO need refactoring
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                repository.reset()
                onResume()
            }

            repository.dataState
                .collect { newState ->
                    _uiState.value = newState.toUIState()

                    if (newState.isFinished) {
                        _navigation.emit(Finish)
                    }
                }
        }
    }

    fun onResume() {
        lastCoroutineTimerJob = viewModelScope.launch(Dispatchers.Default) {
            repository.resume()
        }
    }

    fun onPause() {
        lastCoroutineTimerJob?.cancel()
        repository.pause()
    }

    fun onSkip() {
        lastCoroutineTimerJob?.cancel()
        repository.skipAndPause()
        onResume()
    }

    override fun onCleared() {
        super.onCleared()
        repository.pause()
    }

    private fun TimerDataState.toUIState() =
        TimerUiState(
            currentSet = this.currentSet,
            allSets = this.maxSets,
            type = this.currentType,
            time = this.timeLeftSeconds.toTimeString(),
            progress = this.currentProgress,
            isPaused = this.isPaused
        )
}