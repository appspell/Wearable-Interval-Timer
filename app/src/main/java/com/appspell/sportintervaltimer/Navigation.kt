package com.appspell.sportintervaltimer

sealed class Navigation(open val route: String) {

    object TimerSetup : Navigation(route = "TimerSetup")

    object Timer : Navigation(route = "Timer")

    object Finish : Navigation(route = "Finish")

}