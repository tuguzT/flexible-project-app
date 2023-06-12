package io.github.tuguzt.flexibleproject.view.screens.basic.project

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle
import io.github.tuguzt.flexibleproject.view.utils.SingleLineTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceExposedDropdownMenu(
    allWorkspaces: List<Workspace>,
    workspace: Workspace?,
    onWorkspaceChange: (Workspace?) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = stringResource(R.string.workspace),
) {
    val chooseWorkspace = stringResource(R.string.choose_workspace)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
    ) {
        SingleLineTextField(
            value = workspace?.data?.name ?: chooseWorkspace,
            onValueChange = {},
            label = label,
            modifier = modifier.menuAnchor(),
            readOnly = true,
            enabled = enabled,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
        ) {
            val items = remember(allWorkspaces) {
                listOf(null to chooseWorkspace) +
                    allWorkspaces.map { workspace -> workspace to workspace.data.name }
            }
            items.forEach { (workspace, text) ->
                DropdownMenuItem(
                    text = { OneLineTitle(text = text) },
                    onClick = {
                        onWorkspaceChange(workspace)
                        onExpandedChange(false)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Preview
@Composable
private fun WorkspaceExposedDropdownMenu() {
    AppTheme {
        Surface {
            WorkspaceExposedDropdownMenu(
                allWorkspaces = listOf(),
                workspace = null,
                onWorkspaceChange = {},
                expanded = true,
                onExpandedChange = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
