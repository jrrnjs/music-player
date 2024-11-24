package com.yongjin.musicplayer.feature.player

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yongjin.musicplayer.feature.extensions.toDp
import com.yongjin.musicplayer.feature.extensions.toPx
import kotlin.math.roundToInt


@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    PlayerScreen(
        state = state,
        onPlayClick = {},
        onPrevClick = {},
        onNextClick = {},
        onRepeatClick = {},
        onShuffleClick = {}
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerScreen(
    state: PlayerState,
    onPlayClick: (Boolean) -> Unit,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    onRepeatClick: (RepeatState) -> Unit,
    onShuffleClick: (ShuffleState) -> Unit,
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val screenWidth = constraints.maxWidth.toFloat()

        val collapsedHeight = 72.dp.toPx()
        val expandedHeight = constraints.maxHeight.toFloat()
        val heightRange = expandedHeight - collapsedHeight

        val draggableState = remember {
            AnchoredDraggableState(
                initialValue = State.COLLAPSED,
                anchors = DraggableAnchors {
                    State.COLLAPSED at collapsedHeight
                    State.EXPANDED at expandedHeight
                },
                positionalThreshold = { totalDistance: Float -> totalDistance * 0.5f },
                velocityThreshold = { 500f },
                snapAnimationSpec = tween(),
                decayAnimationSpec = exponentialDecay()
            )
        }

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
            val thumbMinPadding = 8.dp.toPx()
            val thumbMaxVerticalPadding = 72.dp.toPx()
            val thumbMaxHorizontalPadding = 32.dp.toPx()
            val thumbMinSize = 56.dp.toPx()
            val thumbMaxSize = screenWidth - thumbMaxHorizontalPadding * 2

            val thumbVerticalPadding =
                (thumbMaxVerticalPadding - thumbMinPadding) * progress + thumbMinPadding
            val thumbHorizontalPadding =
                (thumbMaxHorizontalPadding - thumbMinPadding) * progress + thumbMinPadding
            val thumbSize = thumbMinSize + progress * (thumbMaxSize - thumbMinSize)

            PlayerThumbnail(
                modifier = Modifier
                    .padding(
                        vertical = thumbVerticalPadding.toDp(),
                        horizontal = thumbHorizontalPadding.toDp()
                    )
                    .size(thumbSize.toDp()),
                song = state.song
            )

            if (progress <= 0.5f) {
                val collapseProgress = progress / 0.5f
                val collapsePlayerPadding = 8.dp.toPx()
                val collapsePlayerStartPadding =
                    thumbMinSize + thumbMinPadding * 2 + collapsePlayerPadding
                val collapsePlayerOffset = -(0.4 * heightDelta).roundToInt()
                PlayerCollapsed(
                    modifier = Modifier
                        .padding(
                            start = collapsePlayerStartPadding.toDp(),
                            end = collapsePlayerPadding.toDp()
                        )
                        .offset { IntOffset(0, collapsePlayerOffset) }
                        .alpha(1 - collapseProgress)
                        .align(Alignment.Center),
                    state = state,
                    onPlayClick = onPlayClick
                )
            }

            if (progress >= 0.5f) {
                val expandProgress = (progress - 0.5f) / 0.5f
                var expandPlayerHeight by remember { mutableStateOf(0) }

                val expandPlayerOffsetRange =
                    expandedHeight - thumbMaxSize - thumbVerticalPadding * 2 - expandPlayerHeight
                val expandPlayerOffset = -(expandProgress * expandPlayerOffsetRange).roundToInt()
                PlayerExpanded(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset {
                            IntOffset(0, expandPlayerOffset)
                        }
                        .padding(horizontal = 32.dp)
                        .align(Alignment.BottomCenter)
                        .alpha(expandProgress)
                        .onGloballyPositioned {
                            expandPlayerHeight = it.size.height
                        },
                    state = state,
                    onPlayClick = onPlayClick,
                    onPrevClick = onPrevClick,
                    onNextClick = onNextClick,
                    onRepeatClick = onRepeatClick,
                    onShuffleClick = onShuffleClick
                )
            }
        }
    }
}

private enum class State {
    COLLAPSED, EXPANDED
}
