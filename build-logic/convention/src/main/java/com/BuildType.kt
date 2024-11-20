package com

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project

internal fun Project.configureBuildType(
    applicationExtension: ApplicationExtension
) {
    applicationExtension.apply {
        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }
}