package com.yongjin.musicplayer.media

import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import com.google.common.util.concurrent.ListenableFuture
import com.yongjin.musicplayer.media.datastore.MediaDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MediaSessionCallback(
    private val mediaDataStore: MediaDataStore,
) : MediaSession.Callback {

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val playerListener: Player.Listener = object : Player.Listener {
        override fun onRepeatModeChanged(repeatMode: Int) {
            coroutineScope.launch(Dispatchers.IO) {
                mediaDataStore.setRepeatMode(repeatMode)
            }
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            coroutineScope.launch(Dispatchers.IO) {
                mediaDataStore.setShuffleMode(shuffleModeEnabled)
            }
        }
    }

    override fun onConnect(
        session: MediaSession,
        controller: MediaSession.ControllerInfo,
    ): MediaSession.ConnectionResult {
        coroutineScope.launch {
            val mediaItems = withContext(Dispatchers.IO) {
                async { mediaDataStore.getMediaItems() }
            }
            val shuffleMode = withContext(Dispatchers.IO) {
                async { mediaDataStore.getShuffleMode() }
            }
            val repeatMode = withContext(Dispatchers.IO) {
                async { mediaDataStore.getRepeatMode() }
            }

            shuffleMode.await()?.let {
                session.player.shuffleModeEnabled = it
            }
            repeatMode.await()?.let {
                session.player.repeatMode = it
            }
            mediaItems.await()?.let {
                session.player.setMediaItems(it)
                session.player.prepare()
            }
        }
        session.player.addListener(playerListener)
        return super.onConnect(session, controller)
    }

    override fun onDisconnected(session: MediaSession, controller: MediaSession.ControllerInfo) {
        session.player.removeListener(playerListener)
        coroutineScope.cancel()
        super.onDisconnected(session, controller)
    }

    @OptIn(UnstableApi::class)
    override fun onSetMediaItems(
        mediaSession: MediaSession,
        controller: MediaSession.ControllerInfo,
        mediaItems: MutableList<MediaItem>,
        startIndex: Int,
        startPositionMs: Long,
    ): ListenableFuture<MediaSession.MediaItemsWithStartPosition> {
        coroutineScope.launch(Dispatchers.IO) {
            mediaDataStore.setMediaItems(mediaItems)
        }
        return super.onSetMediaItems(
            mediaSession,
            controller,
            mediaItems,
            startIndex,
            startPositionMs
        )
    }
}