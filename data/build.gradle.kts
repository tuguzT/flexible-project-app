import io.github.tuguzt.flexibleproject.buildconfig.android.dependency.Kotlin
import io.github.tuguzt.flexibleproject.buildconfig.android.implementation.*

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization")
    id("io.objectbox")
    id("com.apollographql.apollo3")
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

apollo {
    packageName.set("io.github.tuguzt.flexibleproject.data.datasource.remote.model")
}

dependencies {
    implementation(project(":domain"))

    dataImplementation()
    implementation(Kotlin.X.serializationJson)
    implementation(Kotlin.X.datetime)
    graphQLImplementation()

    androidTestImplementation(Kotlin.X.Coroutine.Test.dependency) {
        exclude(
            group = Kotlin.X.group,
            module = Kotlin.X.Coroutine.Test.excludedModule,
        )
    }
    loggingImplementation()
    unitTestingImplementation()
}
