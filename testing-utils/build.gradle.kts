plugins {
    id("library-common-setting")
    id("ktlint-setting")
}

dependencies {

    implementation(libs.bundles.test)
    implementation(libs.coroutine.test)
}