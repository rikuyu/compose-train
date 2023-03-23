buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.gradle.plugin)
        classpath(libs.google.services)
        classpath(libs.hilt.android.gradle.plugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
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