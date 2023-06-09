package io.github.tuguzt.flexibleproject.view.screens.workspace

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.NameTextField

@Composable
fun AddWorkspaceContent(
    name: String,
    onNameChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    visibility: Visibility,
    onVisibilityChange: (Visibility) -> Unit,
    loading: Boolean,
    valid: Boolean,
    onAddWorkspaceClick: () -> Unit,
    onNavigationClick: () -> Unit,
    focusManager: FocusManager = LocalFocusManager.current,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    Scaffold(
        topBar = {
            AddWorkspaceTopBar(
                loading = loading,
                valid = valid,
                onAddWorkspaceClick = onAddWorkspaceClick,
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
        ) {
            NameTextField(
                name = name,
                onNameChange = onNameChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading,
            )
            Spacer(modifier = Modifier.height(16.dp))
            DescriptionTextField(
                description = description,
                onDescriptionChange = onDescriptionChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading,
            )
            Spacer(modifier = Modifier.height(16.dp))

            var expanded by remember { mutableStateOf(false) }
            VisibilityExposedDropdownMenu(
                visibility = visibility,
                onVisibilityChange = onVisibilityChange,
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading,
            )
        }
    }
}

@Preview
@Composable
private fun AddWorkspaceContent() {
    AppTheme {
        AddWorkspaceContent(
            name = "Workspace name",
            onNameChange = {},
            description = "Workspace description",
            onDescriptionChange = {},
            visibility = Visibility.Public,
            onVisibilityChange = {},
            loading = false,
            valid = false,
            onAddWorkspaceClick = {},
            onNavigationClick = {},
        )
    }
}
