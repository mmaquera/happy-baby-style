plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.app.distribution)
    // alias(libs.plugins.hilt)  // Temporalmente deshabilitado
    // alias(libs.plugins.ksp)
}

android {
    namespace = "com.mmaquera.happybabystyle"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.mmaquera.happybabystyle"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            versionNameSuffix = "-debug"
            // Temporarily removed applicationIdSuffix for Google Services compatibility
            // applicationIdSuffix = ".debug"
        }
        
        create("staging") {
            initWith(getByName("debug"))
            isMinifyEnabled = false
            isDebuggable = true
            versionNameSuffix = "-staging"
            // Temporarily removed applicationIdSuffix for Google Services compatibility
            // applicationIdSuffix = ".staging"
            matchingFallbacks += listOf("debug")
        }
        
        release {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug") // TODO: Add release signing config
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    
    // ViewModel and Coroutines
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.kotlinx.coroutines.android)
    
    // Material Icons
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)
    
    // Image Loading
    implementation(libs.coil.kt.coil.compose)
    
    // Navigation Compose
    implementation(libs.androidx.navigation.compose)

    // ConstraintLayout Compose
    implementation(libs.androidx.constraintlayout.compose)

    implementation(libs.kotlinx.serialization.json)
    
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    
    // Ktor Client
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.auth)
    
    // Google Authentication - Credential Manager (Moderno)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services)
    implementation(libs.google.identity.googleid)
    
    // Google Authentication - Legacy (mantener temporalmente)
    implementation(libs.google.auth)
    
    // Hilt Dependency Injection - Temporalmente deshabilitado
    // implementation(libs.hilt.android)
    // implementation(libs.hilt.navigation.compose)
    // ksp(libs.hilt.compiler)
    
    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.arch.core.core.testing)
    testImplementation(libs.mockk)
    
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// Firebase App Distribution Configuration
firebaseAppDistribution {
    // App configuration
    appId = "1:581901746036:android:5a5b5302d77d7ac73bf621"
    
    // Default service account (can be overridden by CI/CD)
    serviceCredentialsFile = project.findProperty("FIREBASE_SERVICE_ACCOUNT_FILE")?.toString()
        ?: "${projectDir}/firebase-service-account.json"
    
    // Default release notes
    releaseNotesFile = "${projectDir}/release-notes.txt"
    
    // Default groups
    groups = "happy-baby-style-testers"
    
    // Note: All build types now use the same app ID since we removed applicationIdSuffix
    // This allows testing different builds with the same Firebase project
}

// Custom distribution tasks for different environments
tasks.register("distributeDebugToFirebase") {
    group = "distribution"
    description = "Distributes debug build to Firebase App Distribution"
    dependsOn("assembleDebug", "appDistributionUploadDebug")
}

tasks.register("distributeStagingToFirebase") {
    group = "distribution"
    description = "Distributes staging build to Firebase App Distribution" 
    dependsOn("assembleStaging", "appDistributionUploadStaging")
}