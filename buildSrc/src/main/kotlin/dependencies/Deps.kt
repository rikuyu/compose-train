object Deps {

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.4.2"

        object Core {
            const val ktx = "androidx.core:core-ktx:1.8.0"
        }

        object Activity {
            const val ktx = "androidx.activity:activity-ktx:1.5.1"
            const val compose = "androidx.activity:activity-compose:1.5.1"
        }

        object Fragment {}

        object Lifecycle {
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1"
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"
        }

        object Navigation {
            const val navigationRuntimeKtx = "androidx.navigation:navigation-runtime-ktx:2.5.1"
            const val navigationCompose = "androidx.navigation:navigation-compose:2.5.1"
        }

        object Room {
            const val runtime = "androidx.room:room-runtime:2.4.3"
            const val compiler = "androidx.room:room-compiler:2.4.3"
            const val ktx = "androidx.room:room-ktx:2.4.3"
        }

        object Hilt {
            const val compiler = "androidx.hilt:hilt-compiler:1.0.0"
            const val navigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"
            const val hiltLifecycleViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha01"
        }

        object Compose {
            const val bom = "androidx.compose:compose-bom:2022.11.00"
            const val compiler = "1.3.2"
            const val ui = "androidx.compose.ui:ui"
            const val uiTooling = "androidx.compose.ui:ui-tooling"
            const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview"

            const val material = "androidx.compose.material:material:1.2.0"
            // const val materialIcons = "androidx.compose.material:material-icons-extended:1.2.0"
        }
    }

    object Material {
        const val material3 = "androidx.compose.material3:material3"
        // const val composeThemeAdapter = "com.google.android.material:compose-theme-adapter:1.1.14"
    }

    object Hilt {
        const val android = "com.google.dagger:hilt-android:2.40"
        const val compiler = "com.google.dagger:hilt-android-compiler:2.40"
        const val hiltAndroidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:2.39.1"
    }

    object Coil {
        const val compose = "io.coil-kt:coil-compose:2.1.0"
    }

    object Ktor {
        const val ktor = "2.0.1"
        const val core = "io.ktor:ktor-client-core:$ktor"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:$ktor"
        const val cio ="io.ktor:ktor-client-cio:$ktor"
        const val serializationGson = "io.ktor:ktor-serialization-gson:$ktor"
    }

    object Kotlin {
        object Coroutines {
            const val coroutines = "1.6.4"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines"
            const val playServices = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$coroutines"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines"
        }
    }

    object Accompanist {
        const val accompanist = "0.27.1"
//        const val systemUIController = "com.google.accompanist:accompanist-systemuicontroller:$accompanist"
//        const val flowLayout = "com.google.accompanist:accompanist-flowlayout:$accompanist"
        const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$accompanist"
        const val placeholder = "com.google.accompanist:accompanist-placeholder-material:$accompanist"
        const val pager = "com.google.accompanist:accompanist-pager:$accompanist"
        const val pagerIndicators = "com.google.accompanist:accompanist-pager-indicators:$accompanist"
    }

    object Testing {
        const val junit = "junit:junit:4.13.2"
        const val testRunner = "androidx.test:runner:1.4.0"
        const val extTestRunner = "androidx.test.ext:junit:1.1.3"
        const val truth = "com.google.truth:truth:1.1.3"
        const val mockk = "io.mockk:mockk:1.12.5"
        const val turbine = "app.cash.turbine:turbine:0.12.1"
    }

    object Firebase {
        const val googleServices = "com.google.gms:google-services:4.3.13"
        const val bom = "com.google.firebase:firebase-bom:30.3.2"
        const val fireStore = "com.google.firebase:firebase-firestore-ktx"
        const val auth = "com.google.firebase:firebase-auth-ktx"
        const val storage = "com.google.firebase:firebase-storage-ktx"
    }

    object Gradle {
        const val androidGradlePlugin = "com.android.tools.build:gradle:7.1.0"
    }

    const val ktlint = "com.pinterest:ktlint:0.45.2"
}