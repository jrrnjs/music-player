package com.yongjin.musicplayer.feature.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.yongjin.musicplayer.designsystem.theme.MusicPlayerTheme
import com.yongjin.musicplayer.feature.R
import com.yongjin.musicplayer.feature.dummyPlayer
import com.yongjin.musicplayer.feature.extensions.toMinuteSecondFormat
import com.yongjin.musicplayer.model.Song
import kotlin.math.roundToLong

@Composable
internal fun PlayerThumbnail(
    song: Song?,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        modifier = modifier.clip(MaterialTheme.shapes.small),
        model = song?.thumbnailUri,
        placeholder = ColorPainter(Color.LightGray),
        error = ColorPainter(Color.LightGray),
        contentDescription = "album art"
    )
}

@Composable
internal fun PlayerCollapsed(
    state: PlayerState,
    onPlayClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = state.song?.title ?: "",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = state.song?.artist ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis
            )
        }

        IconButton(
            onClick = {
                onPlayClick(state.isPlaying)
            }
        ) {
            Icon(
                imageVector = when (state.isPlaying) {
                    true -> ImageVector.vectorResource(R.drawable.baseline_pause_24)
                    false -> ImageVector.vectorResource(R.drawable.baseline_play_arrow_24)
                },
                contentDescription = when (state.isPlaying) {
                    true -> "pause"
                    false -> "play"
                }
            )
        }
    }
}

@Composable
internal fun PlayerExpanded(
    state: PlayerState,
    onPlayClick: (Boolean) -> Unit,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    onRepeatClick: (RepeatState) -> Unit,
    onShuffleClick: (ShuffleState) -> Unit,
    onPositionChanged: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = state.song?.title ?: "",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            modifier = Modifier.padding(top = 6.dp),
            text = state.song?.artist ?: "",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Normal
        )

        var isUserChanging by remember { mutableStateOf(false) }
        var current by remember { mutableLongStateOf(state.currentPosition) }
        val total = state.song?.duration ?: 0

        LaunchedEffect(state.currentPosition) {
            if (!isUserChanging) {
                current = state.currentPosition
            }
        }

        Slider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = current.toFloat(),
            valueRange = 0f..total.toFloat(),
            onValueChange = {
                isUserChanging = true
                current = it.roundToLong()
            },
            onValueChangeFinished = {
                isUserChanging = false
                onPositionChanged(current)
            }
        )

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = current.toMinuteSecondFormat(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                modifier = Modifier.align(Alignment.CenterEnd),
                text = total.toMinuteSecondFormat(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    onRepeatClick(state.repeatState)
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(state.repeatState.icon),
                    contentDescription = state.repeatState.contentDescription
                )
            }
            IconButton(
                onClick = {
                    onPrevClick()
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_skip_previous_24),
                    contentDescription = "skip prev"
                )
            }
            IconButton(
                onClick = {
                    onPlayClick(state.isPlaying)
                }
            ) {
                Icon(
                    imageVector = when (state.isPlaying) {
                        true -> ImageVector.vectorResource(R.drawable.baseline_pause_24)
                        false -> ImageVector.vectorResource(R.drawable.baseline_play_arrow_24)
                    },
                    contentDescription = when (state.isPlaying) {
                        true -> "pause"
                        false -> "play"
                    }
                )
            }
            IconButton(
                onClick = {
                    onNextClick()
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_skip_next_24),
                    contentDescription = "skip next"
                )
            }
            IconButton(
                onClick = {
                    onShuffleClick(state.shuffleState)
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(state.shuffleState.icon),
                    contentDescription = state.shuffleState.contentDescription
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun PlayerCollapsedPreview() {
    MusicPlayerTheme {
        Surface {
            PlayerCollapsed(
                modifier = Modifier
                    .padding(8.dp),
                state = dummyPlayer,
                onPlayClick = {},
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun PlayerExpandedPreview() {
    MusicPlayerTheme {
        Surface {
            PlayerExpanded(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                state = dummyPlayer,
                onPlayClick = {},
                onPrevClick = {},
                onNextClick = {},
                onShuffleClick = {},
                onRepeatClick = {},
                onPositionChanged = {}
            )
        }
    }
}