package dependencies

import Deps
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.tasks.JavaExec
import java.io.File

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