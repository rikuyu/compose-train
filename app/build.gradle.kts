import dependencies.*

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
        kotlinCompilerExtensionVersion = Deps.AndroidX.Compose.composeVersion
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
}

val ktlint by configurations.creating

dependencies {

    implementation(project(path = ":domain"))
    implementation(project(path = ":data"))

    implementation(Deps.AndroidX.Core.ktx)
    implementation(Deps.AndroidX.Compose.ui)
    implementation(Deps.AndroidX.Compose.material)

    implementation(Deps.AndroidX.Compose.uiToolingPreview)
    implementation(Deps.AndroidX.Lifecycle.runtimeKtx)
    implementation(Deps.AndroidX.Lifecycle.viewModelCompose)
    implementation(Deps.AndroidX.Activity.compose)
    implementation(Deps.AndroidX.Navigation.navigationRuntimeKtx)

    implementation(Deps.AndroidX.Navigation.navigationCompose)

    implementation(Deps.Coil.compose)

    implementation(Deps.Kotlin.Coroutines.android)

    implementation(Deps.Material.composeMaterial3)

    accompanist()

    ktor()

    room()

    daggarHilt()

    firebase()

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