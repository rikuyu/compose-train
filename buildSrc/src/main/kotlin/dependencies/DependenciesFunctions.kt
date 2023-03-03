package dependencies

import Deps
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.tasks.JavaExec
import java.io.File

internal fun DependencyHandler.implementation(dependency: Any) =
    add("implementation", dependency)

private fun DependencyHandler.kapt(dependency: Any) =
    add("kapt", dependency)

fun DependencyHandler.room() {
    implementation(Deps.AndroidX.Room.ktx)
    implementation(Deps.AndroidX.Room.runtime)
    kapt(Deps.AndroidX.Room.compiler)
}

fun DependencyHandler.ktor() {
    implementation(Deps.Ktor.core)
    implementation(Deps.Ktor.cio)
    implementation(Deps.Ktor.contentNegotiation)
    implementation(Deps.Ktor.serializationGson)
}

fun DependencyHandler.daggarHilt() {
    implementation(Deps.Hilt.android)
    kapt(Deps.Hilt.compiler)
    implementation(Deps.AndroidX.Hilt.navigationCompose)
    kapt(Deps.AndroidX.Hilt.compiler)
}

fun DependencyHandler.accompanist() {
    implementation(Deps.Accompanist.placeholder)
    implementation(Deps.Accompanist.swipeRefresh)
    implementation(Deps.Accompanist.pager)
    implementation(Deps.Accompanist.pagerIndicators)
}

fun DependencyHandler.firebase() {
    implementation(platform(Deps.Firebase.bom))
    implementation(Deps.Firebase.fireStore)
    implementation(Deps.Firebase.auth)
}

fun DependencyHandler.test() {
    implementation(Deps.Testing.junit)
    implementation(Deps.Testing.extTestRunner)
    implementation(Deps.Testing.mockk)
    implementation(Deps.Testing.truth)
    implementation(Deps.Testing.turbine)
    implementation(Deps.Kotlin.Coroutines.test)
}

fun JavaExec.ktlintArgs(
    inputFiles: ConfigurableFileTree,
    outputPath: String,
    configuration: Configuration,
    buildDir: File,
) {
    inputs.files(inputFiles)
    outputs.dir(outputPath)

    description = "Check Kotlin code style."
    classpath = configuration
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("--android",
        "--color",
        "--reporter=plain",
        "--reporter=checkstyle,output=${buildDir}/reports/ktlint-result.xml",
        "src/**/*.kt")
    isIgnoreExitValue = true
}

fun JavaExec.ktFormatArgs(
    inputFiles: ConfigurableFileTree,
    outputPath: String,
    configuration: Configuration,
) {
    inputs.files(inputFiles)
    outputs.dir(outputPath)

    description = "Fix Kotlin code style deviations."
    classpath = configuration
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("-F", "src/**/*.kt")
    isIgnoreExitValue = true
}