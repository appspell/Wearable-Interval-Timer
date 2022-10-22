package com.appspell.sportintervaltimer.timer

data class TimerUiState(
    val sets: String,
    val type: TimerType,
    val time: String,
    val progress: Float
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
)

enum class TimerType {
    PREPARE, WORK, REST, UNDEFINED
}
