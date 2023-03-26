plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("app-base-setting") {
            id = "app-base-setting"
            implementationClass = "TrainAppSetting"
        }

        register("library-common-setting") {
            id = "library-common-setting"
            implementationClass = "TrainAppLibraryCommonSetting"
        }
    }
}