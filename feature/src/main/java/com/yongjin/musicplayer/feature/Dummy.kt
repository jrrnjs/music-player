package com.yongjin.musicplayer.feature

import android.net.Uri
import com.yongjin.musicplayer.feature.player.PlayerState
import com.yongjin.musicplayer.feature.player.RepeatState
import com.yongjin.musicplayer.feature.player.ShuffleState
import com.yongjin.musicplayer.model.Album
import com.yongjin.musicplayer.model.Song
import kotlinx.collections.immutable.persistentListOf

internal val dummyAlbums = persistentListOf(
    Album(
        id = 1,
        title = "1000 Forms of Fear",
        artist = "Sia",
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Album(
        id = 2,
        title = "30",
        artist = "Adele",
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Album(
        id = 3,
        title = "After Hours",
        artist = "The Weeknd",
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Album(
        id = 4,
        title = "Starboy (Explicit Ver.)",
        artist = "The Weeknd",
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Album(
        id = 5,
        title = "Unstoppable",
        artist = "Sia",
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Album(
        id = 6,
        title = "CHROMAKOPIA",
        artist = "Tyler, The Creator",
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Album(
        id = 7,
        title = "The Star Chapter:Sanctuary (EP)",
        artist = "TOMORROW X TOGETHER",
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Album(
        id = 8,
        title = "Hit Me Hard And Soft",
        artist = "Billie Eilish",
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    )
)

internal val dummySongs = persistentListOf(
    Song(
        id = 1,
        album = "Brother",
        artist = "Daniel Duke",
        title = "Build the Levees",
        duration = 1000 * 60 * 3 + 1000 * 14,
        track = 1,
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Song(
        id = 2,
        album = "Brother",
        artist = "Daniel Duke",
        title = "Borderline",
        duration = 1000 * 60 * 2 + 1000 * 37,
        track = 2,
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Song(
        id = 3,
        album = "Brother",
        artist = "Daniel Duke",
        title = "A Couple Things",
        duration = 1000 * 60 * 3 + 1000 * 45,
        track = 3,
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Song(
        id = 4,
        album = "Brother",
        artist = "Daniel Duke",
        title = "Aster",
        duration = 1000 * 60 * 3 + 1000 * 2,
        track = 4,
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),

    Song(
        id = 5,
        album = "Brother",
        artist = "Daniel Duke",
        title = "Brother",
        duration = 1000 * 60 * 3 + 1000 * 32,
        track = 5,
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Song(
        id = 6,
        album = "Brother",
        artist = "Daniel Duke",
        title = "Tell Me Once",
        duration = 1000 * 60 * 3 + 1000 * 25,
        track = 6,
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),

    Song(
        id = 7,
        album = "Brother",
        artist = "Daniel Duke",
        title = "The Sparrow & the Needle",
        duration = 1000 * 60 * 3,
        track = 7,
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Song(
        id = 8,
        album = "Brother",
        artist = "Daniel Duke",
        title = "The Likes of Love",
        duration = 1000 * 60 * 2 + 1000 * 42,
        track = 8,
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),

    Song(
        id = 9,
        album = "Brother",
        artist = "Daniel Duke",
        title = "Lessons",
        duration = 1000 * 60 * 3 + 1000 * 16,
        track = 9,
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Song(
        id = 10,
        album = "Brother",
        artist = "Daniel Duke",
        title = "Kiss Me in the Dark",
        duration = 1000 * 60 * 3,
        track = 10,
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    ),
    Song(
        id = 11,
        album = "Brother",
        artist = "Daniel Duke",
        title = "She Speaks Spanish",
        duration = 1000 * 60 * 4 + 1000 * 29,
        track = 11,
        thumbnailUri = Uri.EMPTY,
        contentUri = Uri.EMPTY
    )
)

internal val dummyPlayer = PlayerState(
    song = dummySongs.first(),
    playingTime = 1000 * 60 * 1 + 1000 * 11,
    isPlaying = true,
    shuffleState = ShuffleState.SHUFFLE_ON,
    repeatState = RepeatState.REPEAT_ONE
)