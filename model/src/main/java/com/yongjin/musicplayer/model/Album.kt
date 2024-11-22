package com.yongjin.musicplayer.model

import android.net.Uri
import androidx.compose.runtime.Stable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Stable
@Serializable
data class Album(
    val id: Long,
    val title: String?,
    val artist: String?,
    @Serializable(with = UriSerializer::class)
    val thumbnailUri: Uri,
    @Serializable(with = UriSerializer::class)
    val contentUri: Uri,
)

object UriSerializer : KSerializer<Uri> {

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Uri", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Uri {
        return Uri.parse(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: Uri) {
        encoder.encodeString(value.toString())
    }
}