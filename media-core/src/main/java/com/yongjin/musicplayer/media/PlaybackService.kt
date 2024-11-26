package com.yongjin.musicplayer.media

import android.app.PendingIntent
import android.content.Intent
import androidx.annotation.OptIn
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.DefaultMediaNotificationProvider
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.yongjin.musicplayer.media.datastore.MediaDataStore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaybackService : MediaSessionService() {

    private var mediaSession: MediaSession? = null

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()

        // audio focus
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()

        val player = ExoPlayer.Builder(this)
            .setAudioAttributes(audioAttributes, true)
            .build()

        val mediaDataStore = MediaDataStore(this)
        val callback = MediaSessionCallback(mediaDataStore)

        mediaSession = MediaSession.Builder(this, player)
            .setCallback(callback)
            .setSessionActivity(createPendingIntent())
            .build()

        setMediaNotificationProvider(DefaultMediaNotificationProvider(this))
    }

    override fun onDestroy() {
        mediaSession?.player?.release()
        mediaSession?.release()
        mediaSession = null
        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    private fun createPendingIntent(): PendingIntent {
        return PendingIntent.getActivity(
            this,
            0,
            Intent(this, Class.forName("com.yongjin.musicplayer.MainActivity")),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}