plugins {
    alias(libs.plugins.library.common.setting)
    alias(libs.plugins.ktlint.setting)
    alias(libs.plugins.ksp)
}

dependencies {

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)

    ksp(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)
}
