import dependencies.*

plugins {
    id("com.android.library")
    id("android-common-setting")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

val ktlint by configurations.creating

dependencies {

    implementation(project(path = ":model"))

    implementation(libs.coroutine.android)
    implementation(libs.coroutine.play.services)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)

    implementation(libs.ktor.core)
    implementation(libs.ktor.gson)
    implementation(libs.ktor.cio)
    implementation(libs.ktor.negotiation)

    kapt(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    ktlint(Deps.ktlint) {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

val outputDir = "${project.buildDir}/reports/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

val ktlintCheck by tasks.creating(JavaExec::class) {
    ktlintArgs(inputFiles, outputDir, ktlint, buildDir)
}

val ktlintFormat by tasks.creating(JavaExec::class) {
    ktFormatArgs(inputFiles, outputDir, ktlint)
}