plugins {
    // Main plugin for an Android application project
    id("com.android.application")
    // The Kotlin plugin can be removed if not needed, 
    // but keep it if specific libraries require it.
    // For a Java-only project, id("org.jetbrains.kotlin.android") can be removed.
}

android {
    namespace = "com.innerly.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.innerly.app"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        // Enable Java 8 support
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    // Main UI and Android Libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Room Database (Correct way for Java)
    implementation("androidx.room:room-runtime:2.6.1")

    // In Java, use annotationProcessor instead of ksp or kapt
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    // Room Common library for SQLite support
    implementation("androidx.room:room-common:2.6.1")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
