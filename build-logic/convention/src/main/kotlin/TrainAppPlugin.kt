import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class TrainAppPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.android")
                apply("com.android.application")
            }
            extensions.configure<ApplicationExtension> {
                with(defaultConfig) {
                    targetSdk = libs.version("target-sdk").toInt()
                    applicationId = "com.example.composetrainapp"
                    versionCode = 1
                    versionName = "1"
                }
                configureKotlinAndroid(this)
            }
        }
    }
}