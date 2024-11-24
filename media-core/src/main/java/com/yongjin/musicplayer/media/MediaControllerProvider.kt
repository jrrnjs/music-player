package com.yongjin.musicplayer.media

import androidx.media3.session.MediaController
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.guava.await
import javax.inject.Inject

interface MediaControllerProvider {
    suspend fun await(): MediaController
}

class MediaControllerProviderImpl @Inject constructor(
    private val future: ListenableFuture<MediaController>,
) : MediaControllerProvider {

    override suspend fun await(): MediaController {
        return future.await()
    }
}