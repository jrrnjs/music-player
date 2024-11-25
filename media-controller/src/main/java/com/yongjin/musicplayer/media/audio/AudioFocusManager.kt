package com.yongjin.musicplayer.media.audio

import android.content.Context
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AudioFocusManager @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val audioManager: AudioManager? by lazy {
        context.getSystemService<AudioManager>()
    }
    private var focusChangeListener: AudioManager.OnAudioFocusChangeListener? = null

    private var _hasAudioFocus: Boolean = false
    val hasAudioFocus: Boolean get() = _hasAudioFocus

    fun requestAudioFocus(onFocusChange: (Int) -> Unit): Boolean {
        focusChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
            _hasAudioFocus = focusChange == AudioManager.AUDIOFOCUS_GAIN
            onFocusChange(focusChange)
        }

        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val request = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setOnAudioFocusChangeListener(focusChangeListener!!)
                .build()
            audioManager?.requestAudioFocus(request)
        } else {
            audioManager?.requestAudioFocus(
                focusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
            )
        }

        return (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED).also {
            _hasAudioFocus = it
        }
    }
}