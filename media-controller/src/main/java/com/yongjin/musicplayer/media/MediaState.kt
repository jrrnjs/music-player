package com.yongjin.musicplayer.media

import androidx.media3.common.MediaItem

data class MediaState(
    val repeatMode: Int,
    val shuffleModeEnabled: Boolean,
    val isPlaying: Boolean,
    val currentPosition: Long,
    val currentMediaItem: MediaItem?,
)
