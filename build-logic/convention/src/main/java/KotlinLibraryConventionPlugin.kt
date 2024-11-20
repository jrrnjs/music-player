import com.configureKotlinJvm
import com.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KotlinLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin")
            }

            configureKotlinJvm()
            dependencies {
                add("testImplementation", libs.findLibrary("junit").get())
            }
        }
    }
}