package com.appspell.sportintervaltimer.timersetup

data class TimerSetupUIState(
    val sets: String,
    val work: String,
    val rest: String
)

data class TimerSetupDataState(
    val sets: Int,
    val work: Long,
    val rest: Long
)