package com.appspell.sportintervaltimer

sealed class Navigation(open val route: String) {

    data class TimerSetup(override val route: String = "TimerSetup") : Navigation(route = route)

    data class Timer(override val route: String = "Timer") : Navigation(route = route)

    data class Finish(override val route: String = "Finish") : Navigation(route = route)

}