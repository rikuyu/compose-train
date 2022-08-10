plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Versions.compileSdkVersion

    defaultConfig {
        applicationId = Versions.applicationId
        minSdk = Versions.minSdkVersion
        targetSdk = Versions.targetSdkVersion
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-beta03"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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

    implementation(Deps.Accompanist.placeholder)
    implementation(Deps.Accompanist.swipeRefresh)

    implementation(Deps.Ktor.core)
    implementation(Deps.Ktor.cio)
    implementation(Deps.Ktor.contentNegotiation)
    implementation(Deps.Ktor.serializationGson)

    implementation(Deps.AndroidX.Room.ktx)
    implementation(Deps.AndroidX.Room.runtime)
    kapt(Deps.AndroidX.Room.compiler)

    implementation(Deps.Hilt.android)
    kapt(Deps.Hilt.compiler)
    kapt(Deps.AndroidX.Hilt.compiler)
    implementation(Deps.AndroidX.Hilt.navigationCompose)

    ktlint("com.pinterest:ktlint:0.45.2") {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

val outputDir = "${project.buildDir}/reports/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

val ktlintCheck by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Check Kotlin code style."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("--android",
            "--color",
            "--reporter=plain",
            "--reporter=checkstyle,output=${buildDir}/reports/ktlint-result.xml",
            "src/**/*.kt")
    isIgnoreExitValue = true
}

val ktlintFormat by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("-F", "src/**/*.kt")
    isIgnoreExitValue = true
}