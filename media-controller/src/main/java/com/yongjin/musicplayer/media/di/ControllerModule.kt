package com.yongjin.musicplayer.media.di

import com.yongjin.musicplayer.media.PlaybackController
import com.yongjin.musicplayer.media.PlaybackControllerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ControllerModule {

    @Binds
    abstract fun bindsPlaybackController(
        controller: PlaybackControllerImpl,
    ): PlaybackController
}