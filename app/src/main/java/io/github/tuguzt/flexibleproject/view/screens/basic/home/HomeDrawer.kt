package io.github.tuguzt.flexibleproject.view.screens.basic.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.material.placeholder
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.domain.model.Id
import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceData
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.ExpandedIcon
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle
import io.github.tuguzt.flexibleproject.view.utils.UserAvatar
import io.github.tuguzt.flexibleproject.view.utils.WorkspaceImage
import io.github.tuguzt.flexibleproject.view.utils.clickableWithoutRipple

@Composable
fun HomeDrawer(
    drawerContent: HomeContent,
    onUserClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit,
    workspacesExpanded: Boolean,
    onWorkspacesExpandedChange: (Boolean) -> Unit,
    onWorkspaceClick: (Workspace) -> Unit,
    onAddNewWorkspaceClick: () -> Unit,
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    content: @Composable () -> Unit,
) {
    val (user, workspaces) = drawerContent

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                UserContent(
                    content = user,
                    onClick = onUserClick,
                )
                Spacer(modifier = Modifier.height(8.dp))

                WorkspacesContent(
                    content = workspaces,
                    workspacesExpanded = workspacesExpanded,
                    onWorkspacesExpandedChange = onWorkspacesExpandedChange,
                    onWorkspaceClick = onWorkspaceClick,
                    onAddNewWorkspaceClick = onAddNewWorkspaceClick,
                )
                Divider(modifier = Modifier.padding(horizontal = 28.dp, vertical = 8.dp))

                SettingsDrawerItem(onClick = onSettingsClick)
                AboutDrawerItem(onClick = onAboutClick)
            }
        },
        modifier = modifier,
        drawerState = drawerState,
        content = content,
    )
}

@Composable
private fun UserContent(
    content: HomeContent.UserContent,
    onClick: () -> Unit,
) {
    val (user, avatar) = content

    Surface(tonalElevation = 12.dp, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(horizontal = 28.dp, vertical = 16.dp)) {
            Box(
                content = { avatar() },
                modifier = Modifier
                    .height(72.dp)
                    .clickableWithoutRipple(
                        onClick = onClick,
                        enabled = user != null,
                    ),
            )

            Spacer(modifier = Modifier.height(8.dp))
            OneLineTitle(
                text = user?.data?.displayName ?: "Display name",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.placeholder(visible = user == null),
            )

            Spacer(modifier = Modifier.height(4.dp))
            OneLineTitle(
                text = user?.data?.name?.let { "@$it" } ?: "Name",
                modifier = Modifier.placeholder(visible = user == null),
            )
        }
    }
}

@Composable
private fun SettingsDrawerItem(onClick: () -> Unit) {
    NavigationDrawerItem(
        modifier = Modifier.padding(horizontal = 12.dp),
        label = { OneLineTitle(text = stringResource(R.string.settings)) },
        icon = { Icon(Icons.Rounded.Settings, contentDescription = null) },
        selected = false,
        onClick = onClick,
    )
}

@Composable
private fun AboutDrawerItem(onClick: () -> Unit) {
    NavigationDrawerItem(
        modifier = Modifier.padding(horizontal = 12.dp),
        label = { OneLineTitle(text = stringResource(R.string.about)) },
        icon = { Icon(Icons.Rounded.Info, contentDescription = null) },
        selected = false,
        onClick = onClick,
    )
}

@Composable
private fun WorkspacesContent(
    content: HomeContent.WorkspacesContent,
    workspacesExpanded: Boolean,
    onWorkspacesExpandedChange: (Boolean) -> Unit,
    onWorkspaceClick: (Workspace) -> Unit,
    onAddNewWorkspaceClick: () -> Unit,
) {
    val (workspaces, icon) = content

    Column {
        WorkspacesHeader(
            expanded = workspacesExpanded,
            onExpandedChange = onWorkspacesExpandedChange,
        )
        AnimatedVisibility(
            visible = workspacesExpanded,
            enter = expandVertically(expandFrom = Alignment.Top),
            exit = shrinkVertically(shrinkTowards = Alignment.Top),
        ) {
            LazyColumn {
                items(
                    items = workspaces,
                    key = { workspace -> workspace.id.toString() },
                ) { workspace ->
                    WorkspaceDrawerItem(
                        workspace = workspace,
                        icon = { icon(workspace) },
                        onClick = { onWorkspaceClick(workspace) },
                    )
                }
                item {
                    AddNewWorkspaceDrawerItem(
                        onClick = onAddNewWorkspaceClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun WorkspacesHeader(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
) {
    NavigationDrawerItem(
        modifier = Modifier.padding(horizontal = 12.dp),
        label = { OneLineTitle(text = stringResource(R.string.workspaces)) },
        badge = { ExpandedIcon(expanded) },
        selected = false,
        onClick = { onExpandedChange(!expanded) },
    )
}

@Composable
private fun WorkspaceDrawerItem(
    workspace: Workspace,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    NavigationDrawerItem(
        modifier = Modifier.padding(horizontal = 12.dp),
        label = { OneLineTitle(text = workspace.data.name) },
        icon = icon,
        selected = false,
        onClick = onClick,
    )
}

@Composable
private fun AddNewWorkspaceDrawerItem(onClick: () -> Unit) {
    NavigationDrawerItem(
        modifier = Modifier.padding(horizontal = 12.dp),
        label = { OneLineTitle(text = stringResource(R.string.add_new_workspace)) },
        icon = { Icon(Icons.Rounded.Add, contentDescription = null) },
        selected = false,
        onClick = onClick,
    )
}

@Preview
@Composable
private fun HomeDrawer() {
    val user = User(
        id = Id("1"),
        data = UserData(
            name = "tuguzT",
            displayName = "Timur Tugushev",
            role = Role.User,
            email = "timurka.tugushev@gmail.com",
            avatar = "https://avatars.githubusercontent.com/u/56771526",
        ),
    )
    val userContent = HomeContent.UserContent(
        user = user,
        avatar = {
            UserAvatar(
                user = user,
                modifier = Modifier.size(72.dp).clip(CircleShape),
            )
        },
    )
    val workspacesContent = HomeContent.WorkspacesContent(
        workspaces = listOf(
            Workspace(
                id = Id("1"),
                data = WorkspaceData(
                    name = "First workspace",
                    description = "",
                    visibility = Visibility.Public,
                    image = null,
                ),
            ),
            Workspace(
                id = Id("2"),
                data = WorkspaceData(
                    name = "Second workspace",
                    description = "",
                    visibility = Visibility.Public,
                    image = null,
                ),
            ),
        ),
        icon = { workspace ->
            WorkspaceImage(
                workspace = workspace,
                modifier = Modifier.size(24.dp),
            )
        },
    )
    val drawerContent = HomeContent(
        user = userContent,
        workspaces = workspacesContent,
    )

    AppTheme {
        HomeDrawer(
            drawerContent = drawerContent,
            drawerState = rememberDrawerState(DrawerValue.Open),
            onUserClick = {},
            onSettingsClick = {},
            onAboutClick = {},
            workspacesExpanded = true,
            onWorkspacesExpandedChange = {},
            onWorkspaceClick = {},
            onAddNewWorkspaceClick = {},
            content = {},
        )
    }
}
