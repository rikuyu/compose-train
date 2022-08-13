plugins {
    id("android-common-setting")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

dependencies {

    implementation(project(path = ":domain"))

    implementation(Deps.Ktor.core)
    implementation(Deps.Ktor.cio)
    implementation(Deps.Ktor.contentNegotiation)
    implementation(Deps.Ktor.serializationGson)

    implementation(Deps.Kotlin.Coroutines.android)

    implementation(Deps.AndroidX.Room.ktx)
    implementation(Deps.AndroidX.Room.runtime)
    kapt(Deps.AndroidX.Room.compiler)

    implementation(Deps.Hilt.android)
    kapt(Deps.Hilt.compiler)
    implementation(Deps.AndroidX.Hilt.navigationCompose)
    kapt(Deps.AndroidX.Hilt.compiler)
}