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
            implementationClass = "TrainAppPlugin"
        }

        register("library-common-setting") {
            id = "library-common-setting"
            implementationClass = "TrainAppLibraryCommonPlugin"
        }

        register("ktlint-setting") {
            id = "ktlint-setting"
            implementationClass= "KtlintPlugin"
        }
    }
}
