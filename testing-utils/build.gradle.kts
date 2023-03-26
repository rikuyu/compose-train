plugins {
    id("library-common-setting")
    id("ktlint-setting")
}

dependencies {

    implementation(libs.junit)
    implementation(libs.mockk)
    implementation(libs.truth)
    implementation(libs.turbine)
    implementation(libs.androidx.test.junit)
    implementation(libs.coroutine.test)
}