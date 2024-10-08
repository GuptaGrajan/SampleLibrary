


// In your build.gradle.kts
buildscript {
    dependencies {
        classpath(libs.httpBuilder)

        // ... other classpath dependencies
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
//    alias(libs.plugins.tools.build.gradle) apply false
//    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.hilt.android) apply false
//    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.maven.publish) apply false
    alias(libs.plugins.android.maven) apply false
    alias(libs.plugins.bintray) apply false
    alias(libs.plugins.dokka) apply false




}