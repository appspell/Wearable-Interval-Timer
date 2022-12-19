package com.appspell.sportintervaltimer.timersetup

internal fun calculateTotalTime(
    sets: Int,
    workSeconds: Int,
    restSeconds: Int
): Int {
    val total = sets * workSeconds + (sets - 1) * restSeconds
    return if (total < 0) return 0
    else total
}