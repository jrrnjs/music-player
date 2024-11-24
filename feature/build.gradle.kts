plugins {
    id("musicplayer.android.feature")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.yongjin.musicplayer.feature"
}

dependencies {
    implementation(project(":designsystem"))
    implementation(project(":data"))
    implementation(project(":model"))
    implementation(project(":media-core"))
    implementation(libs.kotlinx.serialization.json)
}