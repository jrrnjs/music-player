package com.yongjin.musicplayer.media

import androidx.media3.common.MediaItem
import kotlinx.coroutines.flow.StateFlow

interface PlaybackController {

    val mediaState: StateFlow<MediaState>

    suspend fun play(mediaItem: MediaItem)

    suspend fun play(mediaItems: List<MediaItem>)

    suspend fun play()

    suspend fun pause()

    suspend fun skipPrevious()

    suspend fun skipNext()

    suspend fun changeShuffleMode(enabled: Boolean)

    suspend fun changeRepeatMode(repeatMode: Int)

    suspend fun seekTo(position: Long)

    fun release()
}