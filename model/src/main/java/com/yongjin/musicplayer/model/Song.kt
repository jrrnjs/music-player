package com.yongjin.musicplayer.model

import android.net.Uri
import androidx.compose.runtime.Stable

@Stable
data class Song(
    val id: Long,
    val album: String?,
    val artist: String?,
    val title: String?,
    val duration: Int,
    val track: Int,
    val thumbnailUri: Uri,
    val contentUri: Uri,
) {
    private val min = duration / 60000
    private val sec = duration % 60000 / 1000

    val displayDuration: String = "%d:%02d".format(min, sec)
}