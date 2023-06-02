package io.github.tuguzt.flexibleproject.view.screens.basic

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle

@Composable
fun BasicWorkspaces(
    content: BasicContent.WorkspacesContent,
    onWorkspaceClick: (Workspace) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(
            items = content.workspaces,
            key = { workspace -> workspace.id.toString() },
        ) { workspace ->
            BasicWorkspaceItem(
                workspace = workspace,
                icon = { content.icon(workspace) },
                onClick = { onWorkspaceClick(workspace) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun BasicWorkspaceItem(
    workspace: Workspace,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        tonalElevation = 1.dp,
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon()
            Spacer(modifier = Modifier.width(16.dp))
            OneLineTitle(text = workspace.data.name)
        }
    }
}
