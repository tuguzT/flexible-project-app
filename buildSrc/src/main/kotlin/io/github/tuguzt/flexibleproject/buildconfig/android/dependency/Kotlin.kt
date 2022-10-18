package io.github.tuguzt.flexibleproject.buildconfig.android.dependency

object Kotlin {
    object X {
        private const val version = "1.6.4"
        const val group = "org.jetbrains.kotlinx"

        const val coroutine = "$group:kotlinx-coroutines-android:$version"
        const val playServices = "$group:kotlinx-coroutines-play-services:$version"

        const val serializationJson = "$group:kotlinx-serialization-json:1.3.2"

        object Test {
            const val coroutine = "$group:kotlinx-coroutines-test:$version"
            const val excludedModule = "kotlinx-coroutines-debug"
        }
    }

    object CompilerExtension {
        const val version = "1.3.2"
    }

    const val logger = "io.github.microutils:kotlin-logging-jvm:3.0.2"
}
