package com.yongjin.musicplayer.media.di

import android.content.ComponentName
import android.content.Context
import androidx.media3.session.SessionToken
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
    fun provideSessionToken(
        @ApplicationContext context: Context,
    ): SessionToken {
        return SessionToken(context, ComponentName(context, PlaybackService::class.java))
    }
}