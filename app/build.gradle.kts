plugins {
    alias(libs.plugins.app.base.setting)
    alias(libs.plugins.ktlint.setting)
    alias(libs.plugins.ksp)

    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")

    // id("dev.shreyaspatil.compose-compiler-report-generator") version "1.1.0"
}

dependencies {

    implementation(projects.model)
    implementation(projects.data)
    implementation(projects.testingUtils)
    implementation(projects.feature.catalog)
    implementation(projects.feature.rickandmorty)
    implementation(projects.feature.todo)
    implementation(projects.shared)

    implementation(libs.androidx.core)
    implementation(libs.androidx.core.splashscreen)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.bundles.activity)

    implementation(libs.bundles.lifecycle)

    implementation(libs.navigation.compose)

    implementation(libs.coil.compose)

    implementation(libs.bundles.accompanist)

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

    implementation(libs.coroutine.android)
    implementation(libs.coroutine.test)

    implementation(libs.bundles.test)
}
