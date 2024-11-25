package com.yongjin.musicplayer.media.di

import com.yongjin.musicplayer.media.PlaybackController
import com.yongjin.musicplayer.media.PlaybackControllerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ControllerModule {

    @Binds
    @Singleton
    abstract fun bindsPlaybackController(
        controller: PlaybackControllerImpl,
    ): PlaybackController
}