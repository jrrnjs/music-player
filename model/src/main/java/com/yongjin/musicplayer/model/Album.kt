package com.yongjin.musicplayer.model

import android.net.Uri
import androidx.compose.runtime.Stable

@Stable
data class Album(
    val id: Long,
    val title: String?,
    val artist: String?,
    val thumbnailUri: Uri,
    val contentUri: Uri,
)