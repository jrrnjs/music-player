package com.yongjin.musicplayer.feature.album

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yongjin.musicplayer.designsystem.theme.MusicPlayerTheme
import com.yongjin.musicplayer.feature.dummyAlbums
import com.yongjin.musicplayer.feature.dummySongs
import com.yongjin.musicplayer.model.Album
import com.yongjin.musicplayer.model.Song
import kotlinx.collections.immutable.ImmutableList

@Composable
fun AlbumScreen(
    navigateUp: () -> Unit,
    viewModel: AlbumViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AlbumScreen(
        album = state.album,
        songs = state.songs,
        onNavigationClick = navigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlbumScreen(
    album: Album,
    songs: ImmutableList<Song>,
    onNavigationClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(album.title ?: "") },
                navigationIcon = {
                    IconButton(onClick = onNavigationClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item(key = "header") {
                AlbumHeader(album)
            }
            items(
                items = songs,
                key = { it.id }
            ) {
                SongItem(it)
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun AlbumScreenPreview() {
    MusicPlayerTheme {
        AlbumScreen(
            album = dummyAlbums.first(),
            songs = dummySongs,
            onNavigationClick = {}
        )
    }
}
