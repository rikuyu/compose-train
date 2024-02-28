import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.attributes.Bundling
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.tasks.JavaExec
import org.gradle.kotlin.dsl.*
import java.io.File

class KtlintPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val outputDir = "${project.layout.projectDirectory}/reports/"
            val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

            val ktlint by configurations.creating

            val ktlintCheck by tasks.creating(JavaExec::class) {
                ktlintArgs(inputFiles, outputDir, ktlint)
            }

            val ktlintFormat by tasks.creating(JavaExec::class) {
                ktFormatArgs(inputFiles, outputDir, ktlint)
            }

            dependencies {
                ktlint(libs.findLibrary("ktlint").get()) {
                    attributes {
                        attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
                    }
                }
            }
        }
    }
}

private fun JavaExec.ktlintArgs(
    inputFiles: ConfigurableFileTree,
    outputPath: String,
    configuration: Configuration,
) {
    inputs.files(inputFiles)
    outputs.dir(outputPath)

    description = "Check Kotlin code style."
    classpath = configuration
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf(
        "--android",
        "--color",
        "--reporter=plain",
        "--reporter=checkstyle,output=${outputPath}ktlint-result.xml",
        "src/**/*.kt",
    )
    isIgnoreExitValue = true
}

private fun JavaExec.ktFormatArgs(
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
