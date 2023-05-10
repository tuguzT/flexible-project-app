package io.github.tuguzt.flexibleproject.view.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun ExpandedIcon(expanded: Boolean) {
    val rotation = remember {
        Animatable(initialValue = expanded.toDegrees())
    }
    LaunchedEffect(expanded) {
        val degrees = expanded.toDegrees()
        rotation.animateTo(targetValue = degrees)
    }

    Icon(
        imageVector = Icons.Rounded.ExpandLess,
        contentDescription = null,
        modifier = Modifier.graphicsLayer { rotationZ = rotation.value },
    )
}

private fun Boolean.toDegrees() = if (this) 0f else 180f
