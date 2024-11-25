package com.yongjin.musicplayer.feature.player

import androidx.compose.runtime.Stable

@Stable
data class VolumeState(
    val current: Int,
    val max: Int
)