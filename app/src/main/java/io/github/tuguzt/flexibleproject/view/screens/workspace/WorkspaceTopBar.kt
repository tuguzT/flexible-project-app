package io.github.tuguzt.flexibleproject.view.screens.workspace

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.placeholder.material.placeholder
import io.github.tuguzt.flexibleproject.domain.model.Id
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceData
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.NavigateUpIconButton
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceTopBar(
    workspace: Workspace?,
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = { WorkspaceTitle(workspace = workspace) },
        navigationIcon = { NavigateUpIconButton(onClick = onNavigationClick) },
        actions = actions,
    )
    // TODO workspace image, scroll behaviour
}

@Composable
private fun WorkspaceTitle(workspace: Workspace?) {
    OneLineTitle(
        text = workspace?.data?.name ?: "Placeholder",
        modifier = Modifier.placeholder(visible = workspace == null),
    )
}

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
                WorkspaceTopBar(workspace = workspace, onNavigationClick = {})
            },
        ) { padding ->
            Box(modifier = Modifier.padding(padding))
        }
    }
}

@Preview
@Composable
private fun WorkspaceTopBarWithoutWorkspace() {
    AppTheme {
        Scaffold(
            topBar = {
                WorkspaceTopBar(workspace = null, onNavigationClick = {})
            },
        ) { padding ->
            Box(modifier = Modifier.padding(padding))
        }
    }
}
