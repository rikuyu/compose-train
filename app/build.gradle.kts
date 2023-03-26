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

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.uiTooling)
    implementation(libs.compose.runtime)
    implementation(libs.compose.material3)

    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel.compose)

    implementation(libs.activity.ktx)
    implementation(libs.activity.compose)

    implementation(libs.navigation.compose)

    implementation(libs.coil.compose)

    implementation(libs.coroutine.android)

    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)

    implementation(libs.ktor.core)
    implementation(libs.ktor.gson)
    implementation(libs.ktor.negotiation)
    implementation(libs.ktor.cio)

    kapt(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    implementation(libs.junit)
    implementation(libs.mockk)
    implementation(libs.truth)
    implementation(libs.turbine)
    implementation(libs.androidx.test.junit)
    implementation(libs.coroutine.test)
}
