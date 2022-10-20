package io.github.tuguzt.flexibleproject.buildconfig.android.implementation

import org.gradle.api.artifacts.dsl.DependencyHandler
import io.github.tuguzt.flexibleproject.buildconfig.android.dependency.architecture.Hilt
import io.github.tuguzt.flexibleproject.buildconfig.android.dependency.architecture.AndroidX

fun DependencyHandler.hiltImplementation() {
    kapt(Hilt.compiler)
    implementation(Hilt.dependency)
}

fun DependencyHandler.dataImplementation() {
    implementation(AndroidX.core)
    implementation(AndroidX.security)
}

fun DependencyHandler.appImplementation() {
    implementation(AndroidX.core)
    implementation(AndroidX.lifecycle)
    implementation(AndroidX.splashScreen)
}
