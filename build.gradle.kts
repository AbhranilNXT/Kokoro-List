// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.android.library") version "8.2.1" apply false

    // Add the dependency for the Hilt Gradle plugin
    id("com.google.dagger.hilt.android") version "2.48" apply false

    // Add the dependency for the Kotlin Symbol Processing Gradle plugin
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false

    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.1" apply false
}