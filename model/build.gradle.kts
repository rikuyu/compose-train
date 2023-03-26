plugins {
    id("library-common-setting")
    id("kotlin-kapt")
}

dependencies {

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)

    kapt(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)
}