package io.github.tuguzt.flexibleproject.view.navigation

import androidx.compose.ui.graphics.vector.ImageVector

sealed interface ImageDestination : Destination {
    val icon: ImageVector
}
