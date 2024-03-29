@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.junit5)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.hilt.android)
}

kapt {
    useBuildCache = true
    correctErrorTypes = true
    showProcessorStats = true
}

@Suppress("UnstableApiUsage")
android {
    namespace = libs.versions.android.namespace.get()
    compileSdk = libs.versions.android.sdk.compile.get().toInt()

    defaultConfig {
        applicationId = libs.versions.android.namespace.get()
        minSdk = libs.versions.android.sdk.min.get().toInt()
        targetSdk = libs.versions.android.sdk.target.get().toInt()
        versionCode = libs.versions.app.version.code.get().toInt()
        versionName = libs.versions.app.version.name.get()

        vectorDrawables {
            useSupportLibrary = true
        }

        testInstrumentationRunner = libs.versions.android.test.runner.get()
        testInstrumentationRunnerArguments["runnerBuilder"] = libs.versions.android.test.args.get()
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Clean Architecture layers
    implementation(project(":domain"))
    implementation(project(":data"))

    // MVI
    implementation(libs.mvi)
    implementation(libs.mvi.main)
    implementation(libs.mvi.logging)
    implementation(libs.mvi.coroutines)

    // Android
    implementation(libs.androidx.core)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel)

    // Testing
    testImplementation(libs.junit4)
    testImplementation(libs.junit5.api)
    testImplementation(libs.junit5.params)
    testRuntimeOnly(libs.junit5.engine)
    testRuntimeOnly(libs.junit5.vintage.engine)
    androidTestImplementation(libs.junit4.android)
    androidTestImplementation(libs.junit5.api)
    androidTestImplementation(libs.androidx.espresso)

    // Hilt
    implementation(libs.hilt)
    kapt(libs.hilt.kapt)
    implementation(libs.hilt.navigation)

    // Jetpack Compose
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.material3)
    implementation(libs.compose.material3.window.size)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.compose.ui.test.junit4)

    // Accompanist
    implementation(libs.accompanist.placeholder)

    // Compose Destinations
    implementation(libs.compose.destinations)
    ksp(libs.compose.destinations.ksp)

    // Coil
    implementation(libs.coil.compose)
}
