plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationConventionPlugin") {
            id = "musicplayer.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidLibraryConventionPlugin") {
            id = "musicplayer.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidFeatureConventionPlugin") {
            id = "musicplayer.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        register("kotlinLibraryConventionPlugin") {
            id = "musicplayer.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }

        register("androidHiltConventionPlugin") {
            id = "musicplayer.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidComposeConventionPlugin") {
            id = "musicplayer.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
    }
}