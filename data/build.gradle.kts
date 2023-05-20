@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.junit5)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
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
        minSdk = libs.versions.android.sdk.min.get().toInt()
        targetSdk = libs.versions.android.sdk.target.get().toInt()

        testInstrumentationRunner = libs.versions.android.test.runner.get()
        testInstrumentationRunnerArguments["runnerBuilder"] = libs.versions.android.test.args.get()

        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    // Clean Architecture layers
    implementation(project(":domain"))

    // Kotlin
    implementation(libs.kotlinx.coroutines.core)

    // Android
    implementation(libs.androidx.core)

    // ObjectBox
    implementation(libs.objectbox.kotlin)
    debugImplementation(libs.objectbox.android.objectbrowser)
    releaseImplementation(libs.objectbox.android)

    // Testing
    testImplementation(libs.junit4)
    testImplementation(libs.junit5.api)
    testImplementation(libs.junit5.params)
    testRuntimeOnly(libs.junit5.engine)
    testRuntimeOnly(libs.junit5.vintage.engine)
    androidTestImplementation(libs.junit4.android)
    androidTestImplementation(libs.junit5.api)
    androidTestImplementation(libs.androidx.espresso)
}

apply(plugin = libs.plugins.objectbox.get().pluginId)
