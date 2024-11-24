plugins {
    id("musicplayer.android.library")
    id("musicplayer.android.hilt")
}

android {
    namespace = "com.yongjin.musicplayer.media"
}

dependencies {
    implementation(project(":model"))
    implementation(libs.androidx.media3.session)
    implementation(libs.kotlinx.coroutines.guava)
}