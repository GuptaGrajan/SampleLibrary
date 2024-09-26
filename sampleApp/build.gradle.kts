plugins {
    id ("com.android.library")
//    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("kotlin-parcelize")
    id ("maven-publish")
//    alias(libs.plugins.google.services)
    alias(libs.plugins.hilt.android)
//    alias(libs.plugins.firebase.crashlytics)

}

android {
    signingConfigs {
        create("release") {
        }
    }
    namespace = "com.example.baseproject"
    compileSdk = 34



    defaultConfig {
//        applicationId = "com.example.baseproject"
        minSdk = 24
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

buildFeatures{
    viewBinding = true
    dataBinding = true
}
    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
//            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    packagingOptions {
        resources {
            excludes += listOf("META-INF/DEPENDENCIES", "META-INF/LICENSE", "META-INF/NOTICE")
        }
    }


}

publishing{
    publications{
        register<MavenPublication>("release"){
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.databinding.runtime)
    implementation(libs.play.services.auth.api.phone)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation (libs.retrofit) {
        exclude(group = "commons-collections", module = "commons-collections")
    }
    implementation (libs.converter.gson) {
        exclude(group = "commons-collections", module = "commons-collections")
    }
    implementation (libs.logging.interceptor) {
        exclude(group = "commons-collections", module = "commons-collections")
    }
    implementation (libs.httpclient) {
        exclude(group = "commons-collections", module = "commons-collections")
    }


    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)
    implementation (libs.sdp.android)

    implementation (libs.glide)
    annotationProcessor (libs.compiler)


    implementation (libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.androidx.lifecycle.extensions)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation (libs.androidx.preference.ktx)
    implementation (libs.androidx.legacy.support.v4)
    implementation (libs.gson)
    implementation (libs.glide)
    annotationProcessor (libs.compiler)
    implementation (libs.tedonactivityresult)
    implementation (libs.android.image.cropper)
    implementation (libs.libphonenumber)
    implementation(libs.httpcomponents.httpclient) {
        exclude(group = "commons-collections", module = "commons-collections")
    }
    implementation(libs.okhttp) {
        exclude(group = "commons-collections", module = "commons-collections")
    }
// https://mvnrepository.com/artifact/org.codehaus.groovy.modules.http-builder/http-builder
    implementation("org.codehaus.groovy.modules.http-builder:http-builder:0.7.1"){
        exclude(group = "commons-collections", module = "commons-collections")
    }



}