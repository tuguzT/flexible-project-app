package io.github.tuguzt.flexibleproject.view.screens.basic.home

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

data class HomeContent(
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
fun HomeContent(
    content: HomeContent,
    onMenuClick: () -> Unit,
    onDrawerUserClick: () -> Unit,
    onDrawerSettingsClick: () -> Unit,
    onDrawerAboutClick: () -> Unit,
    drawerWorkspacesExpanded: Boolean,
    onDrawerWorkspacesExpandedChange: (Boolean) -> Unit,
    onDrawerAddNewWorkspaceClick: () -> Unit,
    onAddClick: () -> Unit,
    onWorkspaceClick: (Workspace) -> Unit,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
) {
    HomeDrawer(
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
            topBar = { HomeTopBar(onMenuClick = onMenuClick) },
            floatingActionButton = { HomeAddFAB(onClick = onAddClick) },
        ) { padding ->
            HomeWorkspaces(
                content = content.workspaces,
                onWorkspaceClick = onWorkspaceClick,
                modifier = Modifier.padding(padding),
            )
        }
    }
}

@Composable
private fun HomeAddFAB(
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
