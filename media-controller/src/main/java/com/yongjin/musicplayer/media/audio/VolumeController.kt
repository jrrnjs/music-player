package com.yongjin.musicplayer.media.audio

import android.content.Context
import android.media.AudioManager
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class VolumeController @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val audioManager: AudioManager? by lazy {
        context.getSystemService<AudioManager>()
    }

    fun getCurrentVolume(): Int {
        return audioManager?.getStreamVolume(AudioManager.STREAM_MUSIC) ?: 0
    }

    fun getMaxVolume(): Int {
        return audioManager?.getStreamMaxVolume(AudioManager.STREAM_MUSIC) ?: 0
    }

    fun setVolume(volume: Int) {
        audioManager?.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            volume,
            AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE
        )
    }
}