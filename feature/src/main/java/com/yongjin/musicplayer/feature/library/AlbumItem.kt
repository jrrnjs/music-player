package com.yongjin.musicplayer.feature.library

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.yongjin.musicplayer.designsystem.theme.MusicPlayerTheme
import com.yongjin.musicplayer.model.Album
import kotlinx.collections.immutable.persistentListOf
import kotlin.random.Random

@Composable
fun AlbumItem(album: Album) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                model = album.thumbnailUri,
                placeholder = ColorPainter(Color.LightGray),
                error = ColorPainter(Color.LightGray),
                contentDescription = "album art"
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = album.title ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = album.artist ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(widthDp = 160)
@Preview(widthDp = 160, uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)
@Composable
private fun AlbumPreview() {
    MusicPlayerTheme {
        Surface {
            AlbumItem(
                album = dummies.random()
            )
        }
    }
}

internal val dummies = persistentListOf(
    Album(
        id = Random.nextLong(),
        title = "1000 Forms of Fear",
        artist = "Sia",
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Album(
        id = Random.nextLong(),
        title = "30",
        artist = "Adele",
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Album(
        id = Random.nextLong(),
        title = "After Hours",
        artist = "The Weeknd",
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Album(
        id = Random.nextLong(),
        title = "Starboy (Explicit Ver.)",
        artist = "The Weeknd",
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Album(
        id = Random.nextLong(),
        title = "Unstoppable",
        artist = "Sia",
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Album(
        id = Random.nextLong(),
        title = "CHROMAKOPIA",
        artist = "Tyler, The Creator",
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Album(
        id = Random.nextLong(),
        title = "The Star Chapter:Sanctuary (EP)",
        artist = "TOMORROW X TOGETHER",
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Album(
        id = Random.nextLong(),
        title = "Hit Me Hard And Soft",
        artist = "Billie Eilish",
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    )
)