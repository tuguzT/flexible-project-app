package io.github.tuguzt.flexibleproject.buildconfig.android.implementation

import io.github.tuguzt.flexibleproject.buildconfig.android.dependency.AndroidX
import io.github.tuguzt.flexibleproject.buildconfig.android.dependency.JetpackCompose
import io.github.tuguzt.flexibleproject.buildconfig.android.dependency.Kotlin
import io.github.tuguzt.flexibleproject.buildconfig.android.dependency.QualityAssurance
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.unitTestingImplementation() {
    testImplementation(QualityAssurance.UnitTest.jUnit4)
    androidTestImplementation(AndroidX.UnitTest.jUnit)
}

fun DependencyHandler.loggingImplementation() {
    implementation(Kotlin.logger)
    implementation(AndroidX.Log.logger)
}

fun DependencyHandler.instrumentTestingImplementation() {
    debugImplementation(JetpackCompose.InstrumentTest.layout)
    androidTestImplementation(JetpackCompose.InstrumentTest.jUnit4)
}
