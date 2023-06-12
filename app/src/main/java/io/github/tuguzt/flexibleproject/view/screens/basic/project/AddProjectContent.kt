package io.github.tuguzt.flexibleproject.view.screens.basic.project

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.domain.model.project.Visibility
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.view.theme.AppTheme

@Composable
fun AddProjectContent(
    name: String,
    onNameChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    visibility: Visibility,
    onVisibilityChange: (Visibility) -> Unit,
    allWorkspaces: List<Workspace>,
    workspace: Workspace?,
    onWorkspaceChange: (Workspace?) -> Unit,
    loading: Boolean,
    valid: Boolean,
    onAddProjectClick: () -> Unit,
    onNavigationClick: () -> Unit,
    focusManager: FocusManager = LocalFocusManager.current,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    Scaffold(
        topBar = {
            AddProjectTopBar(
                loading = loading,
                valid = valid,
                onAddProjectClick = onAddProjectClick,
                onNavigationClick = onNavigationClick,
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = focusManager::clearFocus,
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // TODO
        }
    }
}

@Preview
@Composable
private fun AddProjectContent() {
    AppTheme {
        AddProjectContent(
            name = "Project name",
            onNameChange = {},
            description = "Some project description",
            onDescriptionChange = {},
            visibility = Visibility.Public,
            onVisibilityChange = {},
            allWorkspaces = listOf(),
            workspace = null,
            onWorkspaceChange = {},
            loading = false,
            valid = false,
            onAddProjectClick = {},
            onNavigationClick = {},
        )
    }
}
