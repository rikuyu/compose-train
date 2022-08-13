plugins {
    id("android-common-setting")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

dependencies {

    implementation(Deps.Kotlin.Coroutines.android)

    implementation(Deps.AndroidX.Room.ktx)
    implementation(Deps.AndroidX.Room.runtime)
    kapt(Deps.AndroidX.Room.compiler)

    implementation(Deps.Hilt.android)
    kapt(Deps.Hilt.compiler)
    implementation(Deps.AndroidX.Hilt.navigationCompose)
    kapt(Deps.AndroidX.Hilt.compiler)
}