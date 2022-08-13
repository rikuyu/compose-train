package dependencies

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidCommonPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-kapt")
                apply("dagger.hilt.android.plugin")
                apply("com.google.gms.google-services")
            }

            extensions.configure<BaseAppModuleExtension> {
                configureKotlinAndroid(this)

                with(defaultConfig) {
                    applicationId = Versions.applicationId
                    targetSdk = Versions.targetSdkVersion
                    versionCode = Versions.versionCode
                    versionName = Versions.versionName

                    buildTypes {
                        release {
                            isMinifyEnabled = true
                            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                        }
                    }
                }
            }
        }
    }
}