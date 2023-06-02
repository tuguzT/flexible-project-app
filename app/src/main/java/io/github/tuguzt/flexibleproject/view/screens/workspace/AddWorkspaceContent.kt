package io.github.tuguzt.flexibleproject.view.screens.workspace

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle

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
            VisibilityExposedDropdownMenu(
                visibility = visibility,
                onVisibilityChange = onVisibilityChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading,
            )
        }
    }
}

@Composable
private fun NameTextField(
    name: String,
    onNameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        modifier = modifier,
        enabled = enabled,
        label = { OneLineTitle(text = stringResource(R.string.name)) },
        singleLine = true,
    )
}

@Composable
private fun DescriptionTextField(
    description: String,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedTextField(
        value = description,
        onValueChange = onDescriptionChange,
        modifier = modifier,
        enabled = enabled,
        label = { OneLineTitle(text = stringResource(R.string.description)) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VisibilityExposedDropdownMenu(
    visibility: Visibility,
    onVisibilityChange: (Visibility) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        OutlinedTextField(
            value = visibility.toString(),
            onValueChange = {},
            modifier = modifier.menuAnchor(),
            readOnly = true,
            enabled = enabled,
            label = { OneLineTitle(text = stringResource(R.string.visibility)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            val visibilities = Visibility.values()
            visibilities.forEach { visibility ->
                DropdownMenuItem(
                    text = { OneLineTitle(text = visibility.toString()) },
                    onClick = {
                        onVisibilityChange(visibility)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}
