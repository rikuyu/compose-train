import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class TrainAppSetting : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.android")
                apply("com.android.application")
            }
            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                with(defaultConfig) {
                    targetSdk = libs.version("target-sdk").toInt()
                    applicationId = "com.example.composetrainapp"
                    versionCode = 1
                    versionName = "1"

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    vectorDrawables {
                        useSupportLibrary = true
                    }

                    buildTypes {
                        release {
                            isMinifyEnabled = true
                            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                        }
                    }
                    kotlinOptions {
                        freeCompilerArgs = listOf(
                            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                            "-opt-in=androidx.compose.material.ExperimentalMaterialApi"
                        )
                    }
                    buildFeatures {
                        compose = true
                    }
                    composeOptions {
                        kotlinCompilerExtensionVersion = libs.version("compose-compiler")
                    }
                }
            }
        }
    }
}