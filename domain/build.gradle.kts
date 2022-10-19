import io.github.tuguzt.flexibleproject.buildconfig.android.dependency.Kotlin

plugins {
    `java-library`
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(Kotlin.X.datetime)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
