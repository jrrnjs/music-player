package com.yongjin.musicplayer.feature

import android.net.Uri
import com.yongjin.musicplayer.model.Album
import com.yongjin.musicplayer.model.Song
import kotlinx.collections.immutable.persistentListOf
import kotlin.random.Random

internal val dummyAlbums = persistentListOf(
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

internal val dummySongs = persistentListOf(
    Song(
        id = Random.nextLong(),
        album = "Brother",
        artist = "Daniel Duke",
        title = "Build the Levees",
        duration = 1000 * 60 * 3 + 1000 * 14,
        track = 1,
        contentUri = Uri.EMPTY
    ),
    Song(
        id = Random.nextLong(),
        album = "Brother",
        artist = "Daniel Duke",
        title = "Borderline",
        duration = 1000 * 60 * 2 + 1000 * 37,
        track = 2,
        contentUri = Uri.EMPTY
    ),
    Song(
        id = Random.nextLong(),
        album = "Brother",
        artist = "Daniel Duke",
        title = "A Couple Things",
        duration = 1000 * 60 * 3 + 1000 * 45,
        track = 3,
        contentUri = Uri.EMPTY
    ),
    Song(
        id = Random.nextLong(),
        album = "Brother",
        artist = "Daniel Duke",
        title = "Aster",
        duration = 1000 * 60 * 3 + 1000 * 2,
        track = 4,
        contentUri = Uri.EMPTY
    ),

    Song(
        id = Random.nextLong(),
        album = "Brother",
        artist = "Daniel Duke",
        title = "Brother",
        duration = 1000 * 60 * 3 + 1000 * 32,
        track = 5,
        contentUri = Uri.EMPTY
    ),
    Song(
        id = Random.nextLong(),
        album = "Brother",
        artist = "Daniel Duke",
        title = "Tell Me Once",
        duration = 1000 * 60 * 3 + 1000 * 25,
        track = 6,
        contentUri = Uri.EMPTY
    ),

    Song(
        id = Random.nextLong(),
        album = "Brother",
        artist = "Daniel Duke",
        title = "The Sparrow & the Needle",
        duration = 1000 * 60 * 3,
        track = 7,
        contentUri = Uri.EMPTY
    ),
    Song(
        id = Random.nextLong(),
        album = "Brother",
        artist = "Daniel Duke",
        title = "The Likes of Love",
        duration = 1000 * 60 * 2 + 1000 * 42,
        track = 8,
        contentUri = Uri.EMPTY
    ),

    Song(
        id = Random.nextLong(),
        album = "Brother",
        artist = "Daniel Duke",
        title = "Lessons",
        duration = 1000 * 60 * 3 + 1000 * 16,
        track = 9,
        contentUri = Uri.EMPTY
    ),
    Song(
        id = Random.nextLong(),
        album = "Brother",
        artist = "Daniel Duke",
        title = "Kiss Me in the Dark",
        duration = 1000 * 60 * 3,
        track = 10,
        contentUri = Uri.EMPTY
    ),
    Song(
        id = Random.nextLong(),
        album = "Brother",
        artist = "Daniel Duke",
        title = "She Speaks Spanish",
        duration = 1000 * 60 * 4 + 1000 * 29,
        track = 11,
        contentUri = Uri.EMPTY
    )
)