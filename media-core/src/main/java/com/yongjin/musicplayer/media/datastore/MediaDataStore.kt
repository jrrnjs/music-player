package com.yongjin.musicplayer.media.datastore

import android.content.Context
import androidx.annotation.OptIn
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class MediaDataStore(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        PREFERENCE_NAME_MEDIA_CONFIG
    )

    private val mediaItemKey: Preferences.Key<String> by lazy {
        stringPreferencesKey(PREFERENCE_KEY_MEDIA_ITEMS)
    }
    private val shuffleModeKey: Preferences.Key<Boolean> by lazy {
        booleanPreferencesKey(PREFERENCE_KEY_SHUFFLE_MODE)
    }
    private val repeatModeKey: Preferences.Key<Int> by lazy {
        intPreferencesKey(PREFERENCE_KEY_REPEAT_MODE)
    }

    @OptIn(UnstableApi::class)
    suspend fun getMediaItems(): List<MediaItem>? {
        val mediaItemJson = context.dataStore.data.firstOrNull()?.get(mediaItemKey)
        return mediaItemJson?.let { json ->
            Json.decodeFromString(ListSerializer(MediaItemSerializer), json)
        }
    }

    @OptIn(UnstableApi::class)
    suspend fun setMediaItems(mediaItems: List<MediaItem>) {
        val json = Json.encodeToString(ListSerializer(MediaItemSerializer), mediaItems)
        context.dataStore.edit { preferences ->
            preferences[mediaItemKey] = json
        }
    }

    suspend fun getShuffleMode(): Boolean? {
        return context.dataStore.data.firstOrNull()?.get(shuffleModeKey)
    }

    suspend fun setShuffleMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[shuffleModeKey] = enabled
        }
    }

    suspend fun getRepeatMode(): Int? {
        return context.dataStore.data.firstOrNull()?.get(repeatModeKey)
    }

    suspend fun setRepeatMode(repeatMode: Int) {
        context.dataStore.edit { preferences ->
            preferences[repeatModeKey] = repeatMode
        }
    }

    companion object {
        private const val PREFERENCE_NAME_MEDIA_CONFIG = "pref_name_media_config"
        private const val PREFERENCE_KEY_SHUFFLE_MODE = "pref_key_shuffle_mode"
        private const val PREFERENCE_KEY_REPEAT_MODE = "pref_key_repeat_mode"
        private const val PREFERENCE_KEY_MEDIA_ITEMS = "pref_key_media_items"
    }
}