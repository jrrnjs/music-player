package com.yongjin.musicplayer.media.datastore

import android.net.Uri
import android.os.Bundle
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.double
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long

object MediaItemSerializer : KSerializer<MediaItem> {

    private const val KEY_NUMBER_TYPE = "key_number_type"
    private const val KEY_NUMBER_VALUE = "key_number_value"
    private const val VALUE_NUMBER_INT = "int"
    private const val VALUE_NUMBER_LONG = "long"
    private const val VALUE_NUMBER_FLOAT = "float"
    private const val VALUE_NUMBER_DOUBLE = "double"

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("MediaItem", PrimitiveKind.STRING)

    @OptIn(UnstableApi::class)
    override fun serialize(encoder: Encoder, value: MediaItem) {
        val bundle = value.toBundleIncludeLocalConfiguration()
        val jsonbObject = bundle.toJsonObject()
        encoder.encodeSerializableValue(JsonObject.serializer(), jsonbObject)
    }

    @OptIn(UnstableApi::class)
    override fun deserialize(decoder: Decoder): MediaItem {
        val jsonObject = decoder.decodeSerializableValue(JsonObject.serializer())
        val bundle = jsonObject.toBundle()
        return MediaItem.fromBundle(bundle)
    }

    private fun Any?.toJsonElement(): JsonElement {
        return when (this) {
            null -> JsonNull
            is Bundle -> toJsonObject()
            is Boolean -> JsonPrimitive(this)
            is Number -> toJsonObject()
            is String -> JsonPrimitive(this)
            is Uri -> JsonPrimitive(toString())
            else -> throw IllegalStateException("Can't serialize unknown type: $this")
        }
    }

    private fun Number.toJsonObject(): JsonObject {
        val type = when (this) {
            is Int -> VALUE_NUMBER_INT
            is Long -> VALUE_NUMBER_LONG
            is Float -> VALUE_NUMBER_FLOAT
            is Double -> VALUE_NUMBER_DOUBLE
            else -> throw IllegalArgumentException("Unsupported type ${this.javaClass}")
        }
        return JsonObject(
            mapOf(
                KEY_NUMBER_TYPE to JsonPrimitive(type),
                KEY_NUMBER_VALUE to JsonPrimitive(this)
            )
        )
    }

    private fun Bundle.toJsonObject(): JsonObject {
        val map = keySet().associateWith { key ->
            get(key).toJsonElement()
        }
        return JsonObject(map)
    }

    private fun JsonObject.toBundle(): Bundle {
        val bundle = Bundle()
        forEach { (key, element) ->
            when (element) {
                is JsonObject -> {
                    if (element.containsKey(KEY_NUMBER_TYPE) && element.containsKey(KEY_NUMBER_VALUE)) {
                        bundle.putNumber(key, element)
                    } else {
                        bundle.putBundle(key, element.toBundle())
                    }
                }

                is JsonPrimitive -> {
                    when {
                        element.isString -> {
                            val content = element.content
                            if (content.startsWith("content://media")) {
                                bundle.putParcelable(key, Uri.parse(content))
                            } else {
                                bundle.putString(key, content)
                            }
                        }

                        element.booleanOrNull != null -> bundle.putBoolean(key, element.boolean)
                    }
                }

                else -> throw IllegalArgumentException("Unsupported JSON element type: $element")
            }
        }
        return bundle
    }

    private fun Bundle.putNumber(key: String, jsonObject: JsonObject) {
        val numberKey = jsonObject[KEY_NUMBER_TYPE]?.jsonPrimitive?.content
        val numberValue = jsonObject[KEY_NUMBER_VALUE]?.jsonPrimitive ?: JsonPrimitive(0)
        when (numberKey) {
            VALUE_NUMBER_INT -> {
                putInt(key, numberValue.int)
            }

            VALUE_NUMBER_LONG -> {
                putLong(key, numberValue.long)
            }

            VALUE_NUMBER_FLOAT -> {
                putFloat(key, numberValue.float)
            }

            VALUE_NUMBER_DOUBLE -> {
                putDouble(key, numberValue.double)
            }
        }
    }
}