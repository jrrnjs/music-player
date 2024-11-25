package com

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

@Suppress("UnstableApiUsage")
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
            add("implementation", libs.findLibrary("androidx.ui.tooling.preview").get())
            add("debugImplementation", libs.findLibrary("androidx.ui.tooling").get())
        }
    }

    extensions.configure<ComposeCompilerGradlePluginExtension> {
        enableStrongSkippingMode.set(true)
        fun relativeToRootProject(dir: String) =
            rootProject.layout.buildDirectory.dir(projectDir.toRelativeString(rootDir))
                .map { it.dir(dir) }

        metricsDestination.set(relativeToRootProject("compose-metrics"))
        reportsDestination.set(relativeToRootProject("compose-reports"))
    }
}