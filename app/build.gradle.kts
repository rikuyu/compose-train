import dependencies.*
import org.gradle.kotlin.dsl.kotlinOptions

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Versions.compileSdkVersion

    defaultConfig {
        minSdk = Versions.minSdkVersion
        targetSdk = Versions.targetSdkVersion
        applicationId = Versions.applicationId
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Deps.AndroidX.Compose.compiler
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi"
        )
    }
    buildFeatures {
        compose = true
    }
}

val ktlint by configurations.creating

dependencies {

    implementation(project(path = ":model"))
    implementation(project(path = ":data"))
    implementation(project(path = ":testing-utils"))

    implementation(Deps.AndroidX.Core.ktx)

    implementation(platform(Deps.AndroidX.Compose.bom))
    implementation(Deps.AndroidX.Compose.ui)
    implementation(Deps.AndroidX.Compose.uiToolingPreview)

    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel.compose)

    implementation(libs.activity.ktx)
    implementation(libs.activity.compose)

    implementation(libs.navigation.compose)

    implementation(libs.coil.compose)

    implementation(libs.coroutine.android)
    implementation(libs.compose.material3)

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

    ktlint(Deps.ktlint) {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

val outputDir = "${project.buildDir}/reports/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

val ktlintCheck by tasks.creating(JavaExec::class) {
    ktlintArgs(inputFiles, outputDir, ktlint, buildDir)
}

val ktlintFormat by tasks.creating(JavaExec::class) {
    ktFormatArgs(inputFiles, outputDir, ktlint)
}