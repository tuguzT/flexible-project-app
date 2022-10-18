package io.github.tuguzt.flexibleproject.buildconfig.android.dependency

object JetpackCompose {
    const val version = "1.2.1"

    object Core {
        const val ui = "androidx.compose.ui:ui:$version"
        const val activity = "androidx.activity:activity-compose:1.6.0"
        const val navigation = "androidx.navigation:navigation-compose:2.5.2"
    }

    object Material {
        const val core = "com.google.android.material:material:1.7.0-rc01"

        object Icons {
            const val dependency =
                "androidx.compose.material:material-icons-core:$version"
            const val extension =
                "androidx.compose.material:material-icons-extended:$version"
        }

        object You {
            private const val version = "1.0.0-rc01"

            const val dependency = "androidx.compose.material3:material3:$version"
            const val resizableLayout =
                "androidx.compose.material3:material3-window-size-class:$version"
        }
    }

    object Accompanist {
        const val version = "0.25.1"

        object Navigation {
            const val material =
                "com.google.accompanist:accompanist-navigation-material:$version"
            const val animation =
                "com.google.accompanist:accompanist-navigation-animation:$version"
        }

        const val pager = "com.google.accompanist:accompanist-pager:$version"
        const val placeholder =
            "com.google.accompanist:accompanist-placeholder-material:$version"
        const val refreshSwipe =
            "com.google.accompanist:accompanist-swiperefresh:$version"
    }

    object AndroidStudio {
        const val tooling = "androidx.compose.ui:ui-tooling:$version"
        const val preview = "androidx.compose.ui:ui-tooling-preview:$version"
    }

    object ThirdParty {
        object SharedElement {
            const val url = "https://s01.oss.sonatype.org/content/repositories/snapshots"
            const val dependency = "com.mxalbert.sharedelements:shared-elements:0.1.0-SNAPSHOT"
        }

        const val coil = "io.coil-kt:coil-compose:2.2.2"
        const val scalableDp = "com.intuit.sdp:sdp-android:1.1.0"
        const val navigationHilt = "androidx.hilt:hilt-navigation-compose:1.0.0"
    }

    object InstrumentTest {
        const val jUnit4 = "androidx.compose.ui:ui-test-junit4:$version"
        const val layout = "androidx.compose.ui:ui-test-manifest:$version"
    }
}
