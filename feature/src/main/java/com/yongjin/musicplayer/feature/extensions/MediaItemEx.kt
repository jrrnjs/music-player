package com.yongjin.musicplayer.feature.extensions

import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import com.yongjin.musicplayer.model.Song

@OptIn(UnstableApi::class)
fun Song.toMediaItem(): MediaItem {
    return MediaItem.Builder()
        .setMediaId(id.toString())
        .setUri(contentUri)
        .setMediaMetadata(
            MediaMetadata.Builder()
                .setAlbumTitle(album)
                .setArtist(artist)
                .setTitle(title)
                .setDurationMs(duration)
                .setTrackNumber(track)
                .setArtworkUri(thumbnailUri)
                .build()
        ).build()
}

@OptIn(UnstableApi::class)
fun MediaItem.toSong(): Song {
    return Song(
        id = mediaId.toLong(),
        album = mediaMetadata.albumTitle?.toString(),
        artist = mediaMetadata.artist?.toString(),
        title = mediaMetadata.title?.toString(),
        duration = mediaMetadata.durationMs ?: 0,
        track = mediaMetadata.trackNumber ?: 0,
        thumbnailUri = mediaMetadata.artworkUri,
        contentUri = localConfiguration?.uri
    )
}