package io.github.tuguzt.flexibleproject.view.screens.basic.workspace

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.NameTextField

@Composable
fun EditWorkspaceContent(
    name: String,
    onNameChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    visibility: Visibility,
    onVisibilityChange: (Visibility) -> Unit,
    image: String,
    onImageChange: (String) -> Unit,
    loading: Boolean,
    valid: Boolean,
    onSubmit: () -> Unit,
    onNavigationClick: () -> Unit,
    focusManager: FocusManager = LocalFocusManager.current,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    Scaffold(
        topBar = {
            EditWorkspaceTopBar(
                loading = loading,
                valid = valid,
                onSubmit = onSubmit,
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
            NameTextField(
                name = name,
                onNameChange = onNameChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading,
                label = stringResource(R.string.edit_name),
            )
            DescriptionTextField(
                description = description,
                onDescriptionChange = onDescriptionChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading,
                label = stringResource(R.string.edit_description),
            )

            var expanded by remember { mutableStateOf(false) }
            VisibilityExposedDropdownMenu(
                visibility = visibility,
                onVisibilityChange = onVisibilityChange,
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading,
                label = stringResource(R.string.edit_visibility),
            )
        }
    }
}

@Preview
@Composable
private fun EditWorkspaceContent() {
    AppTheme {
        EditWorkspaceContent(
            name = "Workspace name",
            onNameChange = {},
            description = "Workspace description",
            onDescriptionChange = {},
            visibility = Visibility.Public,
            onVisibilityChange = {},
            image = "",
            onImageChange = {},
            loading = false,
            valid = true,
            onSubmit = {},
            onNavigationClick = {},
        )
    }
}
