package com.yongjin.musicplayer.media

import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class PlaybackControllerImpl @Inject constructor(
    private val mediaControllerProvider: MediaControllerProvider,
) : PlaybackController, Player.Listener {

    private var mediaController: MediaController? = null
    private var trackingPositionJob: Job? = null

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val mutex = Mutex()
    private val _mediaState: MutableStateFlow<MediaState> = MutableStateFlow(
        MediaState(
            repeatMode = Player.REPEAT_MODE_OFF,
            shuffleModeEnabled = false,
            isPlaying = false,
            currentPosition = 0,
            currentMediaItem = null
        )
    )

    // PlaybackController
    override val mediaState: StateFlow<MediaState> = _mediaState.asStateFlow()

    init {
        // init MediaController
        coroutineScope.launch {
            mutex.withLock {
                try {
                    initialize()
                } catch (_: Exception) {
                }
            }
        }
    }

    @OptIn(UnstableApi::class)
    override suspend fun play(mediaItem: MediaItem) {
        performAction {
            it.setMediaItem(mediaItem)
            it.prepare()
            it.play()
        }
    }

    override suspend fun play(mediaItems: List<MediaItem>) {
        performAction {
            it.setMediaItems(mediaItems)
            it.prepare()
            it.play()
        }
    }

    override suspend fun play() {
        performAction {
            it.play()
        }
    }

    override suspend fun pause() {
        performAction {
            it.pause()
        }
    }

    override suspend fun skipPrevious() {
        performAction {
            if (it.hasPreviousMediaItem() && it.currentPosition < 5000) {
                it.seekToPreviousMediaItem()
                if (!it.isPlaying) {
                    it.play()
                }
            } else {
                it.seekToDefaultPosition()
            }
        }
    }

    override suspend fun skipNext() {
        performAction {
            if (it.hasNextMediaItem()) {
                it.seekToNextMediaItem()
                if (!it.isPlaying) {
                    it.play()
                }
            }
        }
    }

    override suspend fun changeShuffleMode(enabled: Boolean) {
        performAction {
            it.shuffleModeEnabled = enabled
        }
    }

    override suspend fun changeRepeatMode(repeatMode: Int) {
        performAction {
            it.repeatMode = repeatMode
        }
    }

    override suspend fun seekTo(position: Long) {
        performAction {
            it.seekTo(position)
        }
    }

    override fun release() {
        coroutineScope.cancel()
        mediaController?.removeListener(this)
        mediaController?.release()
        mediaController = null
    }

    // Player.Listener
    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        _mediaState.update { it.copy(currentMediaItem = mediaItem) }
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        _mediaState.update { it.copy(repeatMode = repeatMode) }
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        _mediaState.update { it.copy(shuffleModeEnabled = shuffleModeEnabled) }
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        _mediaState.update { it.copy(isPlaying = isPlaying) }
        if (isPlaying) {
            startTrackingPosition()
        } else {
            trackingPositionJob?.cancel()
            trackingPositionJob = null
        }
    }

    private fun startTrackingPosition() {
        trackingPositionJob?.cancel()
        trackingPositionJob = coroutineScope.launch(Dispatchers.IO) {
            val mediaController = ensureMediaController()
            while (isActive) {
                val currentPosition = withContext(Dispatchers.Main) {
                    mediaController.currentPosition
                }
                _mediaState.update { it.copy(currentPosition = currentPosition) }
                delay(1000)
            }
        }
    }

    // MediaController
    private suspend fun initialize() {
        mediaController = withContext(Dispatchers.IO) {
            mediaControllerProvider.get()
        }.apply {
            addListener(this@PlaybackControllerImpl)
            _mediaState.update {
                it.copy(
                    repeatMode = repeatMode,
                    shuffleModeEnabled = shuffleModeEnabled,
                    isPlaying = isPlaying,
                    currentPosition = currentPosition,
                    currentMediaItem = currentMediaItem
                )
            }
            if (isPlaying) {
                startTrackingPosition()
            }
        }
    }

    private suspend fun ensureMediaController(): MediaController {
        return mutex.withLock {
            if (mediaController == null) {
                initialize()
            }
            mediaController!!
        }
    }

    private suspend fun performAction(action: suspend (MediaController) -> Unit) {
        try {
            val mediaController = ensureMediaController()
            action(mediaController)
        } catch (_: Exception) {
            mediaController?.removeListener(this)
            mediaController?.release()
            mediaController = null
        }
    }
}
