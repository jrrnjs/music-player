plugins {
    id("musicplayer.android.library")
    id("musicplayer.android.compose")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.yongjin.musicplayer.model"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}