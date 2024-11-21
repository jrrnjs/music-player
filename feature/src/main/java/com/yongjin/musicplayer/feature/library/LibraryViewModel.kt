package com.yongjin.musicplayer.feature.library

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yongjin.musicplayer.data.MediaDataSource
import com.yongjin.musicplayer.model.Album
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val mediaDataSource: MediaDataSource,
) : ViewModel() {

    private val _state: MutableStateFlow<LibraryState> = MutableStateFlow(LibraryState.Loading)
    val state: StateFlow<LibraryState> = _state.asStateFlow()

    init {
        getAlbums()
    }

    fun getAlbums() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(LibraryState.Loading)
            try {
                val albums = mediaDataSource.getAlbums()
                _state.emit(LibraryState.Success(albums.toPersistentList()))
            } catch (e: Exception) {
                _state.emit(LibraryState.Failure(e.message ?: "앨범을 불러오지 못했습니다."))
            }
        }
    }
}

@Stable
sealed interface LibraryState {
    data object Loading : LibraryState
    data class Success(
        val albums: ImmutableList<Album>,
    ) : LibraryState

    data class Failure(
        val message: String,
    ) : LibraryState
}