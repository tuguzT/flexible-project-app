package io.github.tuguzt.flexibleproject.buildconfig.android.dependency

import io.github.tuguzt.flexibleproject.buildconfig.android.implementation.implementation
import org.gradle.api.artifacts.dsl.DependencyHandler

object AndroidX {
    const val core = "androidx.core:core-ktx:1.9.0"
    private const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"
    const val security = "androidx.security:security-crypto:1.0.0"
    private const val splashScreen = "androidx.core:core-splashscreen:1.0.0"

    fun DependencyHandler.androidXDataImplementation() {
        implementation(core)
        implementation(security)
    }

    fun DependencyHandler.androidXAppImplementation() {
        androidXDataImplementation()
        implementation(lifecycle)
        implementation(splashScreen)
    }

    object Log {
        const val logger = "org.slf4j:slf4j-android:1.7.36"
    }

    object UnitTest {
        const val jUnit = "androidx.test.ext:junit:1.1.3"
    }
}
