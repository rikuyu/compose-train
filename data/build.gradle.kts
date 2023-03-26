plugins {
    id("library-common-setting")
    id("ktlint-setting")
    id("kotlin-kapt")
}

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
}