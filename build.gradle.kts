buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Deps.Gradle.androidGradlePlugin)
        classpath(Deps.Kotlin.kotlin)
        classpath(Deps.Hilt.hiltAndroidGradlePlugin)
        classpath(Deps.Firebase.googleServices)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}