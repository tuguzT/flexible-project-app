@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.junit5) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.apollo.graphql) apply false
}

buildscript {
    dependencies {
        classpath(libs.objectbox.gradle.plugin)
    }
}

true // Needed to make the Suppress annotation work for the plugins block
