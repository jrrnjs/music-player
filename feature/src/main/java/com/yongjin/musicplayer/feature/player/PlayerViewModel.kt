package com.yongjin.musicplayer.feature.player

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yongjin.musicplayer.feature.extensions.toSong
import com.yongjin.musicplayer.media.PlaybackController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("UnsafeOptInUsageError")
@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playbackController: PlaybackController,
) : ViewModel() {

    private val initialState = PlayerState(
        song = null,
        currentPosition = 0,
        isPlaying = false,
        shuffleState = ShuffleState.SHUFFLE_OFF,
        repeatState = RepeatState.REPEAT_OFF
    )

    val state: StateFlow<PlayerState> = playbackController.mediaState
        .map {
            PlayerState(
                song = it.currentMediaItem?.toSong(),
                currentPosition = it.currentPosition,
                isPlaying = it.isPlaying,
                shuffleState = ShuffleState.from(it.shuffleModeEnabled),
                repeatState = RepeatState.from(it.repeatMode)
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), initialState)

    fun play() {
        viewModelScope.launch {
            playbackController.play()
        }
    }

    fun pause() {
        viewModelScope.launch {
            playbackController.pause()
        }
    }

    fun skipPrevious() {
        viewModelScope.launch {
            playbackController.skipPrevious()
        }
    }

    fun skipNext() {
        viewModelScope.launch {
            playbackController.skipNext()
        }
    }

    fun changeRepeatMode(current: RepeatState) {
        val next = when (current) {
            RepeatState.REPEAT_ALL -> RepeatState.REPEAT_ONE
            RepeatState.REPEAT_ONE -> RepeatState.REPEAT_OFF
            RepeatState.REPEAT_OFF -> RepeatState.REPEAT_ALL
        }.toRepeatMode()

        viewModelScope.launch {
            playbackController.changeRepeatMode(next)
        }
    }

    fun changeShuffleMode(current: ShuffleState) {
        val next = when (current) {
            ShuffleState.SHUFFLE_ON -> ShuffleState.SHUFFLE_OFF
            ShuffleState.SHUFFLE_OFF -> ShuffleState.SHUFFLE_ON
        }.isEnabled()

        viewModelScope.launch {
            playbackController.changeShuffleMode(next)
        }
    }

    fun seekTo(position: Long) {
        viewModelScope.launch {
            playbackController.seekTo(position)
        }
    }
}
