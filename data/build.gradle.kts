import io.github.tuguzt.flexibleproject.buildconfig.android.dependency.AndroidX.androidXDataImplementation
import io.github.tuguzt.flexibleproject.buildconfig.android.dependency.Kotlin
import io.github.tuguzt.flexibleproject.buildconfig.android.implementation.loggingImplementation
import io.github.tuguzt.flexibleproject.buildconfig.android.implementation.retrofitImplementation
import io.github.tuguzt.flexibleproject.buildconfig.android.implementation.roomImplementation
import io.github.tuguzt.flexibleproject.buildconfig.android.implementation.unitTestingImplementation

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("plugin.serialization")
    kotlin("kapt")
}

android {
    namespace = "io.github.tuguzt.flexibleproject.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 23
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":domain"))

    androidXDataImplementation()
    implementation(Kotlin.X.serializationJson)
    roomImplementation()
    retrofitImplementation()

    androidTestImplementation(Kotlin.X.Test.coroutine) {
        exclude(group = Kotlin.X.group, module = Kotlin.X.Test.excludedModule)
    }
    loggingImplementation()
    unitTestingImplementation()
}
