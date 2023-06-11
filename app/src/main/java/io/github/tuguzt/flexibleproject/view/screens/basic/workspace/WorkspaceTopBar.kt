package io.github.tuguzt.flexibleproject.view.screens.basic.workspace

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Groups3
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.material.placeholder
import io.github.tuguzt.flexibleproject.domain.model.Id
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceData
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.ImageByUrl
import io.github.tuguzt.flexibleproject.view.utils.ImageError
import io.github.tuguzt.flexibleproject.view.utils.NavigateUpIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceTopBar(
    workspace: Workspace?,
    loading: Boolean,
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    val collapsedFraction by remember {
        derivedStateOf { scrollBehavior?.state?.collapsedFraction ?: 0.0f }
    }
    val titleCollapsed by remember {
        derivedStateOf { collapsedFraction > 0.2f }
    }
    val collapsed by remember {
        derivedStateOf { collapsedFraction > 0.8 }
    }

    val surfaceColor = MaterialTheme.colorScheme.surface
    val containerColor by remember {
        derivedStateOf { surfaceColor.copy(alpha = collapsedFraction) }
    }
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val contentColor by remember {
        derivedStateOf { if (collapsed) onSurfaceColor else Color.White }
    }
    val onSurfaceVariantColor = MaterialTheme.colorScheme.onSurfaceVariant
    val actionContentColor by remember {
        derivedStateOf { if (collapsed) onSurfaceVariantColor else Color.White }
    }

    val gradient = remember {
        val cornerColor = Color.Black.copy(alpha = 0.25f)
        val colors = listOf(cornerColor, Color.Transparent, cornerColor)
        Brush.verticalGradient(colors)
    }

    Box {
        ImageByUrl(
            url = workspace?.data?.image,
            modifier = Modifier
                .matchParentSize()
                .drawWithContent {
                    drawContent()
                    drawRect(gradient)
                },
            error = {
                ImageError(imageVector = Icons.Rounded.Groups3, imageSize = 96.dp)
            },
        )
        LargeTopAppBar(
            modifier = modifier,
            title = {
                WorkspaceTitle(
                    workspace = workspace,
                    collapsed = titleCollapsed,
                )
            },
            navigationIcon = {
                NavigateUpIconButton(
                    onClick = onNavigationClick,
                    enabled = !loading,
                )
            },
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = containerColor,
                navigationIconContentColor = contentColor,
                titleContentColor = contentColor,
                actionIconContentColor = actionContentColor,
            ),
            actions = actions,
        )
        AnimatedVisibility(
            visible = loading,
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                strokeCap = StrokeCap.Round,
            )
        }
    }
}

@Composable
private fun WorkspaceTitle(
    workspace: Workspace?,
    collapsed: Boolean,
) {
    Text(
        text = workspace?.data?.name ?: "Placeholder",
        modifier = Modifier.placeholder(visible = workspace == null),
        maxLines = if (collapsed) 1 else 2,
        overflow = TextOverflow.Ellipsis,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun WorkspaceTopBarWithWorkspace() {
    val workspace = Workspace(
        id = Id("1"),
        data = WorkspaceData(
            name = "First workspace",
            description = "",
            visibility = Visibility.Public,
            image = null,
        ),
    )

    AppTheme {
        Scaffold(
            topBar = {
                WorkspaceTopBar(
                    workspace = workspace,
                    loading = false,
                    onNavigationClick = {},
                )
            },
        ) { padding ->
            Box(modifier = Modifier.padding(padding))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun WorkspaceTopBarWithoutWorkspace() {
    AppTheme {
        Scaffold(
            topBar = {
                WorkspaceTopBar(
                    workspace = null,
                    loading = false,
                    onNavigationClick = {},
                )
            },
        ) { padding ->
            Box(modifier = Modifier.padding(padding))
        }
    }
}
