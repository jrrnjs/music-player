package com.yongjin.musicplayer.model

import android.net.Uri

data class Song(
    val id: Long,
    val album: String?,
    val artist: String?,
    val title: String?,
    val duration: Int,
    val track: Int,
    val contentUri: Uri,
)