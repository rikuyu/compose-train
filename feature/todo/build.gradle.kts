plugins {
    alias(libs.plugins.library.common.setting)
    alias(libs.plugins.ktlint.setting)
    id("kotlin-kapt")

    // id("dev.shreyaspatil.compose-compiler-report-generator") version "1.1.0"
}

dependencies {

    implementation(project(path = ":shared"))
    implementation(project(path = ":model"))
    implementation(project(path = ":data"))

    implementation(libs.coroutine.android)
    implementation(libs.coroutine.play.services)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.bundles.lifecycle)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    implementation(libs.bundles.accompanist)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)
}
