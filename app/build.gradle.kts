plugins {
    id("app-base-setting")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

val ktlint by configurations.creating

dependencies {

    implementation(project(path = ":model"))
    implementation(project(path = ":data"))
    implementation(project(path = ":testing-utils"))

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.uiTooling)
    implementation(libs.compose.runtime)
    implementation(libs.compose.material3)

    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel.compose)

    implementation(libs.activity.ktx)
    implementation(libs.activity.compose)

    implementation(libs.navigation.compose)

    implementation(libs.coil.compose)

    implementation(libs.coroutine.android)

    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)

    implementation(libs.ktor.core)
    implementation(libs.ktor.gson)
    implementation(libs.ktor.negotiation)
    implementation(libs.ktor.cio)

    kapt(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    implementation(libs.junit)
    implementation(libs.mockk)
    implementation(libs.truth)
    implementation(libs.turbine)
    implementation(libs.androidx.test.junit)
    implementation(libs.coroutine.test)

    ktlint(libs.ktlint) {
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
