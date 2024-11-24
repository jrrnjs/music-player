package com.yongjin.musicplayer.feature.player

import androidx.compose.runtime.Stable
import com.yongjin.musicplayer.feature.R
import com.yongjin.musicplayer.model.Song

@Stable
data class PlayerState(
    val song: Song,
    val playingTime: Int,
    val isPlaying: Boolean,
    val shuffleState: ShuffleState,
    val repeatState: RepeatState,
)

@Stable
enum class ShuffleState(
    val icon: Int,
    val contentDescription: String,
) {
    SHUFFLE_ON(
        R.drawable.baseline_shuffle_on_24,
        "shuffle on"
    ),
    SHUFFLE_OFF(
        R.drawable.baseline_shuffle_24,
        "shuffle off"
    )
}

@Stable
enum class RepeatState(
    val icon: Int,
    val contentDescription: String,
) {
    REPEAT_ALL(
        R.drawable.baseline_repeat_on_24,
        "repeat all"
    ),
    REPEAT_ONE(
        R.drawable.baseline_repeat_one_on_24,
        "repeat one"
    ),
    REPEAT_OFF(
        R.drawable.baseline_repeat_24,
        "repeat off"
    )
}