plugins {
    alias(libs.plugins.library.common.setting)
    alias(libs.plugins.ktlint.setting)
    alias(libs.plugins.ksp)
}

dependencies {

    implementation(projects.model)

    implementation(libs.coroutine.android)
    implementation(libs.coroutine.play.services)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)

    implementation(libs.bundles.ktor)

    ksp(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)
}
