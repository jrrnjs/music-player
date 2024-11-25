package com.yongjin.musicplayer.feature.player

import android.content.res.Configuration
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yongjin.musicplayer.designsystem.theme.MusicPlayerTheme
import com.yongjin.musicplayer.feature.dummyPlayer
import com.yongjin.musicplayer.feature.extensions.toDp
import com.yongjin.musicplayer.feature.extensions.toPx
import kotlin.math.roundToInt


@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val volumeState by viewModel.volumeState.collectAsStateWithLifecycle()

    PlayerScreen(
        state = state,
        volumeState = volumeState,
        onPlayClick = { isPlaying ->
            if (isPlaying) {
                viewModel.pause()
            } else {
                viewModel.play()
            }
        },
        onPrevClick = viewModel::skipPrevious,
        onNextClick = viewModel::skipNext,
        onRepeatClick = viewModel::changeRepeatMode,
        onShuffleClick = viewModel::changeShuffleMode,
        onPositionChanged = viewModel::seekTo,
        onVolumeChanged = viewModel::setVolume
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PlayerScreen(
    state: PlayerState,
    volumeState: VolumeState,
    onPlayClick: (Boolean) -> Unit,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    onRepeatClick: (RepeatState) -> Unit,
    onShuffleClick: (ShuffleState) -> Unit,
    onPositionChanged: (Long) -> Unit,
    onVolumeChanged: (Int) -> Unit,
    draggableValue: DraggableValue = DraggableValue.COLLAPSED,
) {
    val configuration = LocalConfiguration.current

    val density = LocalDensity.current
    val navigationBarInset = WindowInsets.Companion.navigationBars.getBottom(density)
    val statusBarInset = WindowInsets.Companion.statusBars.getTop(density)

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val screenWidth = constraints.maxWidth.toFloat()

        val collapsedHeight = PlayerDefaults.collapsedHeight.toPx() + navigationBarInset
        val expandedHeight = constraints.maxHeight.toFloat()

        val velocityThreshold = 100.dp.toPx()
        val draggableState = remember {
            AnchoredDraggableState(
                initialValue = draggableValue,
                anchors = DraggableAnchors {
                    DraggableValue.COLLAPSED at collapsedHeight
                    DraggableValue.EXPANDED at expandedHeight
                },
                positionalThreshold = { totalDistance: Float -> totalDistance * 0.5f },
                velocityThreshold = { velocityThreshold },
                snapAnimationSpec = tween(),
                decayAnimationSpec = exponentialDecay()
            )
        }

        val heightRange = expandedHeight - collapsedHeight
        val heightDelta by remember {
            derivedStateOf { draggableState.offset - collapsedHeight }
        }
        val progress = (heightDelta / heightRange).coerceIn(0f, 1f)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .align(Alignment.BottomCenter)
                .anchoredDraggable(draggableState, reverseDirection = true, Orientation.Vertical)
                .navigationBarsPadding()
                .height(draggableState.offset.toDp())
        ) {
            // thumbnail
            val thumbPadding = with(PlayerDefaults) {
                (expandedPadding - collapsedPadding) * progress + collapsedPadding
            }
            val thumbTopPadding = (statusBarInset * progress).toDp()

            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                val thumbMinSize = PlayerDefaults.collapsedHeight.toPx()
                val thumbSize = ((screenWidth - thumbMinSize) * progress + thumbMinSize).toDp()
                PlayerThumbnail(
                    modifier = Modifier
                        .padding(top = thumbTopPadding)
                        .size(thumbSize)
                        .padding(thumbPadding),
                    song = state.song
                )
            } else {
                PlayerThumbnail(
                    modifier = Modifier
                        .padding(top = thumbTopPadding)
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .padding(thumbPadding),
                    song = state.song
                )
            }

            // collapsed
            if (progress <= 0.5f) {
                val collapsedProgress = progress / 0.5f
                val collapsedPlayerOffset = -(0.4 * heightDelta).roundToInt()
                PlayerCollapsed(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = PlayerDefaults.collapsedHeight + PlayerDefaults.collapsedPadding,
                            end = PlayerDefaults.collapsedPadding
                        )
                        .offset { IntOffset(0, collapsedPlayerOffset) }
                        .alpha(1 - collapsedProgress)
                        .align(Alignment.Center),
                    state = state,
                    onPlayClick = onPlayClick
                )

                if (state.song?.duration != null) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(1 - collapsedProgress)
                            .align(Alignment.BottomCenter),
                        progress = {
                            state.currentPosition / state.song.duration.toFloat()
                        }
                    )
                }
            }

            // expanded
            if (progress >= 0.5f) {
                if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    val expandedProgress = (progress - 0.5f) / 0.5f
                    var expandPlayerHeight by remember { mutableStateOf(0) }

                    val expandPlayerOffsetRange =
                        expandedHeight - screenWidth - statusBarInset - navigationBarInset - expandPlayerHeight
                    val expandPlayerOffset =
                        -(expandedProgress * expandPlayerOffsetRange).roundToInt()
                    PlayerExpanded(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset {
                                IntOffset(0, expandPlayerOffset)
                            }
                            .padding(horizontal = 32.dp)
                            .align(Alignment.BottomCenter)
                            .alpha(expandedProgress)
                            .onGloballyPositioned {
                                expandPlayerHeight = it.size.height
                            },
                        state = state,
                        volumeState = volumeState,
                        onPlayClick = onPlayClick,
                        onPrevClick = onPrevClick,
                        onNextClick = onNextClick,
                        onRepeatClick = onRepeatClick,
                        onShuffleClick = onShuffleClick,
                        onPositionChanged = onPositionChanged,
                        onVolumeChanged = onVolumeChanged
                    )
                } else {
                    val expandedProgress = (progress - 0.5f) / 0.5f
                    val expandPlayerWidth =
                        (screenWidth - (expandedHeight - navigationBarInset - statusBarInset)).toDp()
                    PlayerExpanded(
                        modifier = Modifier
                            .padding(top = statusBarInset.dp / 2)
                            .width(expandPlayerWidth)
                            .padding(end = PlayerDefaults.expandedPadding)
                            .align(Alignment.CenterEnd)
                            .alpha(expandedProgress),
                        state = state,
                        volumeState = volumeState,
                        onPlayClick = onPlayClick,
                        onPrevClick = onPrevClick,
                        onNextClick = onNextClick,
                        onRepeatClick = onRepeatClick,
                        onShuffleClick = onShuffleClick,
                        onPositionChanged = onPositionChanged,
                        onVolumeChanged = onVolumeChanged
                    )
                }
            }
        }
    }
}

enum class DraggableValue {
    COLLAPSED, EXPANDED
}

object PlayerDefaults {

    val collapsedHeight: Dp = 72.dp
    val collapsedPadding: Dp = 8.dp

    val expandedPadding: Dp = 48.dp
}

@PreviewScreenSizes
@Composable
private fun PlayerScreenPreview() {
    MusicPlayerTheme {
        Surface {
            PlayerScreen(
                state = dummyPlayer,
                volumeState = VolumeState(3, 15),
                onPlayClick = {},
                onPrevClick = {},
                onNextClick = {},
                onRepeatClick = {},
                onShuffleClick = {},
                onPositionChanged = {},
                onVolumeChanged = {}
            )
        }
    }
}

@PreviewScreenSizes
@Composable
private fun PlayerScreenExpandedPreview() {
    MusicPlayerTheme {
        Surface {
            PlayerScreen(
                state = dummyPlayer,
                volumeState = VolumeState(3, 15),
                onPlayClick = {},
                onPrevClick = {},
                onNextClick = {},
                onRepeatClick = {},
                onShuffleClick = {},
                onPositionChanged = {},
                onVolumeChanged = {},
                draggableValue = DraggableValue.EXPANDED
            )
        }
    }
}