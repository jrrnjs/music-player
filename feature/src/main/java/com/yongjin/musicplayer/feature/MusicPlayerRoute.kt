package com.yongjin.musicplayer.feature

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.yongjin.musicplayer.model.Album
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

sealed interface MusicPlayerRoute {

    @Serializable
    data object Library : MusicPlayerRoute

    @Serializable
    data class AlbumDetail(val album: Album) : MusicPlayerRoute {
        companion object {
            val typeMap = mapOf(typeOf<Album>() to AlbumNavType)
        }
    }
}

object AlbumNavType : NavType<Album>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): Album? {
        return bundle.getString(key)?.let {
            Json.decodeFromString(it)
        }
    }

    override fun put(bundle: Bundle, key: String, value: Album) {
        bundle.putString(key, Json.encodeToString(value))
    }

    override fun parseValue(value: String): Album {
        return Json.decodeFromString(Uri.decode(value))
    }

    override fun serializeAsValue(value: Album): String {
        return Uri.encode(Json.encodeToString(value))
    }
}