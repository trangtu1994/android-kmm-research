plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.simpletmdbapp2.android"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.example.simpletmdbapp2.android"
        minSdk = 33
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.compose.material3)
    implementation(libs.koin.androidx.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)

    implementation(platform("androidx.compose:compose-bom:2025.05.00"))
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")
    implementation(libs.androidx.navigation.compose.android)

    debugImplementation(libs.compose.ui.tooling)

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // For instrumentation tests in src/androidTest/java
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.01")) // For Compose tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4") // For Compose tests


}