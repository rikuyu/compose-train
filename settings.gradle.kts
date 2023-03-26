pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "ComposeTrainApp"

includeBuild("build-logic")

include(":app")
include(":data")
include(":model")
include(":testing-utils")
