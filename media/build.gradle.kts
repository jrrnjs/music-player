plugins {
    id("musicplayer.android.library")
}

android {
    namespace = "com.yongjin.musicplayer.media"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.media3.exoplayer)
}