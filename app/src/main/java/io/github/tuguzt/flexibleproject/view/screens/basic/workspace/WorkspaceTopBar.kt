package io.github.tuguzt.flexibleproject.view.screens.basic.workspace

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.placeholder.material.placeholder
import io.github.tuguzt.flexibleproject.domain.model.Id
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceData
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
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
    val collapsed by remember {
        derivedStateOf {
            val collapsedFraction = scrollBehavior?.state?.collapsedFraction ?: 0f
            collapsedFraction > 0.2f
        }
    }

    Box {
        LargeTopAppBar(
            modifier = modifier,
            title = {
                WorkspaceTitle(
                    workspace = workspace,
                    collapsed = collapsed,
                )
            },
            navigationIcon = {
                NavigateUpIconButton(
                    onClick = onNavigationClick,
                    enabled = !loading,
                )
            },
            actions = actions,
            scrollBehavior = scrollBehavior,
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
    // TODO workspace image
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
