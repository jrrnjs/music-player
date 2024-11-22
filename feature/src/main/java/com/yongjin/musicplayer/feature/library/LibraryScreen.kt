package com.yongjin.musicplayer.feature.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yongjin.musicplayer.designsystem.EmptyUI
import com.yongjin.musicplayer.designsystem.theme.MusicPlayerTheme
import com.yongjin.musicplayer.feature.dummyAlbums
import com.yongjin.musicplayer.model.Album
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    navigateAlbum: (Album) -> Unit,
    viewModel: LibraryViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("라이브러리") })
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (state) {
                is LibraryState.Success -> AlbumList(
                    albums = (state as LibraryState.Success).albums,
                    onClickItem = navigateAlbum
                )

                is LibraryState.Loading -> Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }

                is LibraryState.Failure -> EmptyUI(
                    message = (state as LibraryState.Failure).message,
                    button = "재시도",
                    onClick = viewModel::getAlbums
                )
            }
        }
    }
}

@Composable
private fun AlbumList(
    albums: ImmutableList<Album>,
    onClickItem: (Album) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        columns = GridCells.Adaptive(160.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = albums,
            key = { it.id }
        ) { album ->
            AlbumItem(
                album = album,
                onClickItem = onClickItem
            )
        }
    }
}

@Composable
@PreviewLightDark
@Preview(name = "Fold", device = Devices.PIXEL_FOLD)
@Preview(name = "Tablet", device = Devices.PIXEL_TABLET)
private fun AlbumListPreview() {
    MusicPlayerTheme {
        Surface {
            AlbumList(
                albums = dummyAlbums,
                onClickItem = {}
            )
        }
    }
}
