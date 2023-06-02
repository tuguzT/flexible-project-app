package io.github.tuguzt.flexibleproject.view.screens.basic

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace

data class BasicContent(
    val user: UserContent,
    val workspaces: WorkspacesContent,
) {
    data class UserContent(
        val user: User?,
        val avatar: @Composable () -> Unit,
    )

    data class WorkspacesContent(
        val workspaces: List<Workspace>,
        val icon: @Composable (Workspace) -> Unit,
    )
}

@Composable
fun BasicContent(
    content: BasicContent,
    onMenuClick: () -> Unit,
    onDrawerUserClick: () -> Unit,
    onDrawerSettingsClick: () -> Unit,
    onDrawerAboutClick: () -> Unit,
    drawerWorkspacesExpanded: Boolean,
    onDrawerWorkspacesExpandedChange: (Boolean) -> Unit,
    onDrawerAddNewWorkspaceClick: () -> Unit,
    onAddClick: () -> Unit,
    sheetExpanded: Boolean,
    onSheetExpandedChange: (Boolean) -> Unit,
    onAddWorkspaceClick: () -> Unit,
    onAddProjectClick: () -> Unit,
    onAddMethodologyClick: () -> Unit,
    onWorkspaceClick: (Workspace) -> Unit,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
) {
    BasicDrawer(
        drawerContent = content,
        drawerState = drawerState,
        onUserClick = onDrawerUserClick,
        onSettingsClick = onDrawerSettingsClick,
        onAboutClick = onDrawerAboutClick,
        workspacesExpanded = drawerWorkspacesExpanded,
        onWorkspacesExpandedChange = onDrawerWorkspacesExpandedChange,
        onWorkspaceClick = onWorkspaceClick,
        onAddNewWorkspaceClick = onDrawerAddNewWorkspaceClick,
    ) {
        Scaffold(
            topBar = { BasicTopBar(onMenuClick = onMenuClick) },
            floatingActionButton = { BasicAddFAB(onClick = onAddClick) },
        ) { padding ->
            BasicWorkspaces(
                content = content.workspaces,
                onWorkspaceClick = onWorkspaceClick,
                modifier = Modifier.padding(padding),
            )
        }
        BasicBottomSheet(
            expanded = sheetExpanded,
            onExpandedChange = onSheetExpandedChange,
            onAddWorkspaceClick = onAddWorkspaceClick,
            onAddProjectClick = onAddProjectClick,
            onAddMethodologyClick = onAddMethodologyClick,
        )
    }
}

@Composable
private fun BasicAddFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = stringResource(R.string.add),
        )
    }
}
