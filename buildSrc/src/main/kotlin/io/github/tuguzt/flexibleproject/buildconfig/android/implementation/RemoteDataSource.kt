package io.github.tuguzt.flexibleproject.buildconfig.android.implementation

import org.gradle.api.artifacts.dsl.DependencyHandler
import io.github.tuguzt.flexibleproject.buildconfig.android.dependency.remote.GraphQL

fun DependencyHandler.graphQLImplementation() {
    implementation(GraphQL.runtime)
}
