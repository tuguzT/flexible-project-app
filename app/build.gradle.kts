import io.github.tuguzt.flexibleproject.buildconfig.android.dependency.Kotlin
import io.github.tuguzt.flexibleproject.buildconfig.android.implementation.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "io.github.tuguzt.flexibleproject"
    compileSdk = 33

    defaultConfig {
        applicationId = "io.github.tuguzt.flexibleproject"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Kotlin.CompilerExtension.version
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(Kotlin.X.Coroutine.android)
    implementation(Kotlin.X.Coroutine.playServices)
    implementation(Kotlin.X.datetime)
    appImplementation()
    composeCoreImplementation()
    materialDesignImplementation()
    navigationImplementation()
    composeThirdPartyImplementation()
    accompanistFeatureImplementation()
    hiltImplementation()

    androidTestImplementation(Kotlin.X.Coroutine.Test.dependency) {
        exclude(
            group = Kotlin.X.group,
            module = Kotlin.X.Coroutine.Test.excludedModule,
        )
    }
    loggingImplementation()
    unitTestingImplementation()
    instrumentTestingImplementation()
}
