package com.appspell.sportintervaltimer.utils

private const val TIME_SEPARATOR = " : "

fun Int.toTimeString(): String {
    val min = this / 60
    val second = this - min * 60
    return "${min.showTwoDigits()}$TIME_SEPARATOR${second.showTwoDigits()}"
}

fun Int.showTwoDigits(): String =
    if (this < 10) "0$this" else this.toString()
