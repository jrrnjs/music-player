plugins {
    id("musicplayer.android.library")
    id("musicplayer.android.compose")
}

android {
    namespace = "com.yongjin.musicplayer.designsystem"
}

dependencies {
    implementation(libs.androidx.material3)
}