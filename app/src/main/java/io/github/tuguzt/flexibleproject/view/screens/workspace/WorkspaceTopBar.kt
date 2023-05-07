package io.github.tuguzt.flexibleproject.view.screens.workspace

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.flexibleproject.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.NavigateUpIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceTopBar(
    workspace: Workspace,
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = workspace.name, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        navigationIcon = { NavigateUpIconButton(onClick = onNavigationClick) },
    )
    // TODO workspace image, scroll behaviour
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun WorkspaceTopBar() {
    val workspace = Workspace(
        id = "1",
        name = "First workspace",
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
