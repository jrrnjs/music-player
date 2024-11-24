package com.yongjin.musicplayer.media

import android.content.Context
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.guava.await
import javax.inject.Inject

class MediaControllerProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sessionToken: SessionToken,
) {
    suspend fun get(): MediaController {
        return MediaController.Builder(context, sessionToken).buildAsync().await()
    }
}