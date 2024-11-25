package com.yongjin.musicplayer.feature.player

import androidx.compose.runtime.Stable
import androidx.media3.common.Player
import com.yongjin.musicplayer.feature.R
import com.yongjin.musicplayer.model.Song

@Stable
data class PlayerState(
    val song: Song?,
    val currentPosition: Long,
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
    );

    fun isEnabled(): Boolean {
        return when (this) {
            SHUFFLE_ON -> true
            SHUFFLE_OFF -> false
        }
    }

    companion object {
        fun from(enabled: Boolean): ShuffleState {
            return if (enabled) SHUFFLE_ON else SHUFFLE_OFF
        }
    }
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
    );

    fun toRepeatMode(): Int {
        return when (this) {
            REPEAT_ALL -> Player.REPEAT_MODE_ALL
            REPEAT_ONE -> Player.REPEAT_MODE_ONE
            REPEAT_OFF -> Player.REPEAT_MODE_OFF
        }
    }

    companion object {
        fun from(repeatMode: Int): RepeatState {
            return when (repeatMode) {
                Player.REPEAT_MODE_ALL -> REPEAT_ALL
                Player.REPEAT_MODE_ONE -> REPEAT_ONE
                Player.REPEAT_MODE_OFF -> REPEAT_OFF
                else -> throw IllegalArgumentException("지원하지 않는 repeatMode 입니다.")
            }
        }
    }
}