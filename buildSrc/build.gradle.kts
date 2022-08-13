plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    // ここで Deps を参照するのは無理みたい
    implementation("com.android.tools.build:gradle:7.1.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.39.1")
    implementation("com.google.gms:google-services:4.3.13")
}
