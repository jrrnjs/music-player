package com.yongjin.musicplayer.data

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.yongjin.musicplayer.model.Album
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MediaDataSource @Inject constructor(
    @ApplicationContext
    private val context: Context,
) {

    private val contentResolver: ContentResolver
        get() = context.contentResolver

    suspend fun getAlbums(): List<Album> = withContext(Dispatchers.IO) {
        val albums = mutableListOf<Album>()

        val uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM_ID,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.ARTIST,
        )

        val cursor = contentResolver.query(uri, projection, null, null, null)

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Albums._ID)
            val albumIdColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ID)
            val albumColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM)
            val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Albums.ARTIST)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val albumId = it.getLong(albumIdColumn)
                val album = it.getString(albumColumn)
                val artist = it.getString(artistColumn)
                val thumbnailUri = ContentUris.withAppendedId(EXTERNAL_ALBUM_ART_URI, albumId)
                val contentUri = ContentUris.withAppendedId(uri, id)
                albums.add(
                    Album(
                        id,
                        album,
                        artist,
                        thumbnailUri,
                        contentUri
                    )
                )
            }
        }
        return@withContext albums
    }

    companion object {
        private val EXTERNAL_ALBUM_ART_URI = Uri.parse("content://media/external/audio/albumart")
    }
}