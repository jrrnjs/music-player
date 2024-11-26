plugins {
    id("musicplayer.android.library")
    id("musicplayer.android.hilt")
}

android {
    namespace = "com.yongjin.musicplayer.media"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.datastore.preferecens)
    implementation(libs.kotlinx.serialization.json)
}