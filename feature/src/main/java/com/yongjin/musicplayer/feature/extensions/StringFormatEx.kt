package com.yongjin.musicplayer.feature.extensions

fun Long.toMinuteSecondFormat(): String {
    val min = this / 60000
    val sec = this % 60000 / 1000
    return "%d:%02d".format(min, sec)
}