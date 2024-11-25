package com.yongjin.musicplayer.model

import android.net.Uri
import androidx.compose.runtime.Stable

@Stable
data class Song(
    val id: Long,
    val album: String?,
    val artist: String?,
    val title: String?,
    val duration: Long,
    val track: Int,
    val thumbnailUri: Uri?,
    val contentUri: Uri?,
)