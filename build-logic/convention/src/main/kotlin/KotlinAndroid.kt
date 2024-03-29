import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    with(commonExtension) {
        compileSdk = libs.version("compile-sdk").toInt()

        defaultConfig {
            minSdk = libs.version("min-sdk").toInt()
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()

            val args = buildList {
                if (this@with is ApplicationExtension) {
                    addAll(
                        listOf(
                            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                            "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
                        ),
                    )
                }
//  compose metrics
//                addAll(
//                    listOf(
//                        "-P",
//                        "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
//                                project.layout.projectDirectory + "/compose-reports",
//                    ),
//                )
//                addAll(
//                    listOf(
//                        "-P",
//                        "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
//                                project.layout.projectDirectory + "/compose-reports",
//                    ),
//                )
            }
            freeCompilerArgs = args
        }

        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = libs.version("compose-compiler")
        }
    }
}

fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}
