plugins {
    id("app-base-setting")
    id("ktlint-setting")

    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

dependencies {

    implementation(project(path = ":model"))
    implementation(project(path = ":data"))
    implementation(project(path = ":testing-utils"))

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

    kapt(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    implementation(libs.coroutine.android)
    implementation(libs.coroutine.test)

    implementation(libs.bundles.test)
}
