package com.yongjin.musicplayer.feature.player

import androidx.lifecycle.ViewModel
import com.yongjin.musicplayer.feature.album.AlbumState
import com.yongjin.musicplayer.feature.dummyPlayer
import com.yongjin.musicplayer.model.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(

) : ViewModel() {

    private val _state: MutableStateFlow<PlayerState> = MutableStateFlow(
        dummyPlayer
    )
    val state: StateFlow<PlayerState> = _state.asStateFlow()


}