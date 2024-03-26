plugins {
    alias(libs.plugins.library.common.setting)
    alias(libs.plugins.ktlint.setting)
    alias(libs.plugins.ksp)

    // id("dev.shreyaspatil.compose-compiler-report-generator") version "1.1.0"
}

dependencies {

    implementation(projects.shared)
    implementation(projects.model)
    implementation(projects.data)

    implementation(libs.coroutine.android)
    implementation(libs.coroutine.play.services)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.bundles.lifecycle)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    implementation(libs.bundles.accompanist)
}
