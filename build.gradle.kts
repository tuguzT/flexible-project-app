plugins {
    id("com.android.application") version "7.3.1" apply false
    id("com.android.library") version "7.3.1" apply false
    kotlin("jvm") version "1.7.20" apply false
    kotlin("android") version "1.7.20" apply false
    kotlin("plugin.serialization") version "1.7.20" apply false
    id("com.apollographql.apollo3") version("3.6.2") apply false
}

buildscript {
    dependencies {
        classpath(
            io.github.tuguzt.flexibleproject.buildconfig.android.dependency.architecture.Hilt.plugin
        )
        classpath(
            io.github.tuguzt.flexibleproject.buildconfig.android.dependency.local.ObjectBox.plugin
        )
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
