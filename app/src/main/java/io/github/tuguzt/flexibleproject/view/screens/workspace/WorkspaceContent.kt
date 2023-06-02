package io.github.tuguzt.flexibleproject.view.screens.workspace

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceData
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.view.theme.AppTheme

@Composable
fun WorkspaceContent(
    workspace: Workspace?,
    onNavigationClick: () -> Unit,
    topBarActions: @Composable RowScope.() -> Unit = {},
) {
    Scaffold(
        topBar = {
            WorkspaceTopBar(
                workspace = workspace,
                onNavigationClick = onNavigationClick,
                actions = topBarActions,
            )
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding))
        // TODO add some content
    }
}

@Preview
@Composable
private fun WorkspaceContentWithWorkspace() {
    val workspace = Workspace(
        id = WorkspaceId("workspace"),
        data = WorkspaceData(
            name = "Some workspace",
            description = "",
            visibility = Visibility.Public,
            imageUrl = null,
        ),
    )
    AppTheme {
        WorkspaceContent(
            workspace = workspace,
            onNavigationClick = {},
        )
    }
}

@Preview
@Composable
private fun WorkspaceContentWithoutWorkspace() {
    AppTheme {
        WorkspaceContent(
            workspace = null,
            onNavigationClick = {},
        )
    }
}
