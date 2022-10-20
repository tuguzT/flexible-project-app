package io.github.tuguzt.flexibleproject.buildconfig.android.dependency.architecture

object Hilt {
    private const val version = "2.44"

    const val compiler = "com.google.dagger:hilt-android-compiler:$version"
    const val dependency = "com.google.dagger:hilt-android:$version"

    const val plugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
}
