package com.yongjin.musicplayer.media.di

import android.content.ComponentName
import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionToken
import com.yongjin.musicplayer.media.MediaSessionCallback
import com.yongjin.musicplayer.media.PlaybackService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MediaModule {

    @Provides
    fun providesExoPlayer(
        @ApplicationContext context: Context,
    ): ExoPlayer {
        return ExoPlayer.Builder(context)
            .build()
    }

    @Provides
    fun provideMediaSession(
        @ApplicationContext context: Context,
        player: ExoPlayer,
    ): MediaSession {
        return MediaSession.Builder(context, player)
            .setCallback(MediaSessionCallback())
            .build()
    }

    @Provides
    fun provideSessionToken(
        @ApplicationContext context: Context,
    ): SessionToken {
        return SessionToken(context, ComponentName(context, PlaybackService::class.java))
    }
}