plugins {
    id("musicplayer.android.feature")
}

android {
    namespace = "com.yongjin.musicplayer.feature"
}

dependencies {
    implementation(project(":designsystem"))
    implementation(libs.accompanist.permissions)
}