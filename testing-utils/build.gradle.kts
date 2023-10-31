plugins {
    id("library-common-setting")
    id("ktlint-setting")
}

dependencies {

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.bundles.test)
    implementation(libs.coroutine.test)
}
