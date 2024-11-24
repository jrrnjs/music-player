package com.yongjin.musicplayer.feature.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
internal fun PlayerThumbnail(
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        modifier = modifier.clip(MaterialTheme.shapes.small),
        model = "https://i.ebayimg.com/images/g/n8IAAOSwltRkNCSF/s-l1200.png",
        placeholder = ColorPainter(Color.LightGray),
        error = ColorPainter(Color.LightGray),
        contentDescription = "album art"
    )
}

@Composable
internal fun PlayerCollapsed(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "우리의 이야기",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "MeloMance",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis
            )
        }

        IconButton(
            onClick = {

            }) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_play_arrow_24),
                contentDescription = "play"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlayerExpanded(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "우리의 이야기",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            modifier = Modifier.padding(top = 6.dp),
            text = "MeloMance",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Normal
        )

        Slider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            state = SliderState()
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {}
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_repeat_24),
                    contentDescription = "repeat"
                )
            }
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {}
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_skip_previous_24),
                    contentDescription = "skip prev"
                )
            }
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {}
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_play_arrow_24),
                    contentDescription = "play"
                )
            }
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {}
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_skip_next_24),
                    contentDescription = "skip next"
                )
            }
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {}
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_shuffle_24),
                    contentDescription = "shuffle"
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
                    .padding(8.dp)
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
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}