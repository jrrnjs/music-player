package com.yongjin.musicplayer.media.di

import com.yongjin.musicplayer.media.MediaControllerProvider
import com.yongjin.musicplayer.media.MediaControllerProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MediaControllerModule {

    @Binds
    abstract fun bindsMediaController(
        mediaControllerProvider: MediaControllerProviderImpl
    ): MediaControllerProvider
}