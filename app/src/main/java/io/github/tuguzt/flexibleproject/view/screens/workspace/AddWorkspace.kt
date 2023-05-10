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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle
import io.github.tuguzt.flexibleproject.viewmodel.workspace.AddWorkspaceViewModel
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.AddWorkspaceStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.AddWorkspaceStore.Label
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun AddWorkspaceScreen(
    navigator: DestinationsNavigator,
    viewModel: AddWorkspaceViewModel = hiltViewModel(),
) {
    val state by viewModel.stateFlow.collectAsState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            AddWorkspaceTopBar(
                loading = state.loading,
                workspaceValid = state.valid,
                onAddWorkspaceClick = {
                    val intent = Intent.CreateWorkspace
                    viewModel.accept(intent)
                },
                onNavigationClick = navigator::navigateUp,
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
                name = state.name,
                onNameChange = { name ->
                    val intent = Intent.ChangeName(name)
                    viewModel.accept(intent)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.loading,
            )
            Spacer(modifier = Modifier.height(16.dp))
            DescriptionTextField(
                description = state.description,
                onDescriptionChange = { description ->
                    val intent = Intent.ChangeDescription(description)
                    viewModel.accept(intent)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.loading,
            )
            Spacer(modifier = Modifier.height(16.dp))
            VisibilityExposedDropdownMenu(
                visibility = state.visibility,
                onVisibilityChange = { visibility ->
                    val intent = Intent.ChangeVisibility(visibility)
                    viewModel.accept(intent)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.loading,
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.labels.collectLatest { label ->
            when (label) {
                is Label.WorkspaceCreated -> navigator.navigateUp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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

@OptIn(ExperimentalMaterial3Api::class)
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
