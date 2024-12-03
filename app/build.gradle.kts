plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.skinsure"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.skinsure"
        minSdk = 34
        targetSdk = 31 // Updated to match compileSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        kotlinCompilerExtensionVersion = "1.4.3" // Check for the latest version
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.0") // Example version
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1") // Example version
    implementation("androidx.activity:activity-compose:1.9.1") // Example version
    implementation(platform("androidx.compose:compose-bom:2023.10.00")) // Example version
    implementation("androidx.compose.ui:ui:1.5.0") // Example version
    implementation("androidx.compose.ui:ui-graphics:1.5.0") // Example version
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0") // Example version
    implementation("androidx.compose.material3:material3:1.1.0") // Example version
    implementation("com.google.mlkit:text-recognition:16.0.1") // Example version
    implementation("com.google.firebase:firebase-firestore:25.1.1")
    implementation("com.google.firebase:firebase-auth:23.1.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2") // Example version
    implementation("androidx.navigation:navigation-compose:2.8.3") // Example version
    implementation("io.coil-kt:coil-compose:2.5.0") // Example version
    implementation("com.google.accompanist:accompanist-permissions:0.34.0") // Example version
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // Example version
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.2") // Example version
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.00")) // Example version
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.0") // Example version
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.0") // Example version
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.0") // Example version
}