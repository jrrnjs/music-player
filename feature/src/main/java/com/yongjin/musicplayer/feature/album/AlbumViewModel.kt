package com.yongjin.musicplayer.feature.album

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yongjin.musicplayer.data.MediaDataSource
import com.yongjin.musicplayer.feature.MusicPlayerRoute
import com.yongjin.musicplayer.model.Album
import com.yongjin.musicplayer.model.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val mediaDataSource: MediaDataSource,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val album = savedStateHandle.toRoute<MusicPlayerRoute.AlbumDetail>(
        MusicPlayerRoute.AlbumDetail.typeMap
    ).album

    private val _state: MutableStateFlow<AlbumState> = MutableStateFlow(
        AlbumState(album, persistentListOf())
    )
    val state: StateFlow<AlbumState> = _state.asStateFlow()

    init {
        getSongs(albumId = album.id)
    }

    private fun getSongs(albumId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val songs = runCatching {
                mediaDataSource.getSongsByAlbum(albumId)
            }.getOrDefault(emptyList())

            _state.emit(
                _state.value.copy(songs = songs.toPersistentList())
            )
        }
    }
}

@Stable
data class AlbumState(
    val album: Album,
    val songs: ImmutableList<Song>,
)
