package com.yongjin.musicplayer.feature.album

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.yongjin.musicplayer.designsystem.theme.MusicPlayerTheme
import com.yongjin.musicplayer.feature.R
import com.yongjin.musicplayer.feature.dummyAlbums
import com.yongjin.musicplayer.feature.dummySongs
import com.yongjin.musicplayer.feature.extensions.toMinuteSecondFormat
import com.yongjin.musicplayer.feature.extensions.toTrackFormat
import com.yongjin.musicplayer.model.Album
import com.yongjin.musicplayer.model.Song

@Composable
fun AlbumHeader(
    album: Album,
    onPlayAlbumClick: () -> Unit,
    onShuffleClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Card {
                AsyncImage(
                    modifier = Modifier.size(72.dp),
                    model = album.thumbnailUri,
                    contentDescription = "album art"
                )
            }

            Column(
                modifier = Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = album.title ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = album.artist ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = onPlayAlbumClick
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_play_arrow_24),
                    contentDescription = "play"
                )
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = onShuffleClick
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
fun SongItem(
    song: Song,
    onSongClick: (Song) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .clickable { onSongClick(song) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = song.track.toTrackFormat(),
            style = MaterialTheme.typography.titleLarge
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = song.title ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${song.artist ?: ""} · ${song.duration.toMinuteSecondFormat()}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "more"
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun AlbumHeaderPreview() {
    MusicPlayerTheme {
        Surface {
            AlbumHeader(
                album = dummyAlbums.first(),
                onPlayAlbumClick = {},
                onShuffleClick = {}
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SongItemPreview() {
    MusicPlayerTheme {
        Surface {
            SongItem(
                song = dummySongs.first(),
                onSongClick = {}
            )
        }
    }
}
