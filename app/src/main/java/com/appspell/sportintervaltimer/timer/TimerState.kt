package com.appspell.sportintervaltimer.timer

data class TimerUiState(
    val currentSet: Int,
    val allSets: Int,
    val type: TimerType,
    val time: String,
    val progress: Float,
    val isPaused: Boolean,
)

data class TimerDataState(
    val maxSets: Int,
    val prepareSeconds: Int,
    val workSeconds: Int,
    val restSeconds: Int,

    val currentType: TimerType,
    val currentIteration: Int,
    val currentSet: Int,
    val timeLeftSeconds: Int,
    val currentRoundEndMillis: Long,
    val currentProgress: Float,

    val isPaused: Boolean,
    val isFinished: Boolean
)

enum class TimerType {
    PREPARE, WORK, REST, UNDEFINED
}
