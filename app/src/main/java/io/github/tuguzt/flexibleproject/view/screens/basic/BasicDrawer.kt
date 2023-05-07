package io.github.tuguzt.flexibleproject.view.screens.basic

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.rounded.Groups3
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.domain.model.Id
import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceData
import io.github.tuguzt.flexibleproject.view.theme.AppTheme

data class BasicDrawerContent(
    val user: UserContent,
    val workspaces: WorkspacesContent,
) {
    data class UserContent(
        val user: User,
        val avatar: @Composable () -> Unit,
    )

    data class WorkspacesContent(
        val workspaces: List<Workspace>,
        val icon: @Composable (Workspace) -> Unit,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicDrawer(
    drawerContent: BasicDrawerContent,
    onUserClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit,
    onWorkspaceClick: (Workspace) -> Unit,
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
                    onWorkspaceClick = onWorkspaceClick,
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
    content: BasicDrawerContent.UserContent,
    onClick: () -> Unit,
) {
    val (user, avatar) = content

    Surface(tonalElevation = 12.dp, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(horizontal = 28.dp, vertical = 16.dp)) {
            Box(
                content = { avatar() },
                modifier = Modifier
                    .height(72.dp)
                    .clickable(onClick = onClick),
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = user.data.displayName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "@${user.data.name}", maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsDrawerItem(onClick: () -> Unit) {
    NavigationDrawerItem(
        modifier = Modifier.padding(horizontal = 12.dp),
        label = { Text(text = stringResource(R.string.settings)) },
        icon = { Icon(Icons.Rounded.Settings, contentDescription = null) },
        selected = false,
        onClick = onClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AboutDrawerItem(onClick: () -> Unit) {
    NavigationDrawerItem(
        modifier = Modifier.padding(horizontal = 12.dp),
        label = { Text(text = stringResource(R.string.about)) },
        icon = { Icon(Icons.Rounded.Info, contentDescription = null) },
        selected = false,
        onClick = onClick,
    )
}

@Composable
private fun WorkspacesContent(
    content: BasicDrawerContent.WorkspacesContent,
    onWorkspaceClick: (Workspace) -> Unit,
) {
    val (workspaces, icon) = content

    Column {
        Text(
            text = stringResource(R.string.workspaces),
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 16.dp),
        )
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WorkspaceDrawerItem(
    workspace: Workspace,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    NavigationDrawerItem(
        modifier = Modifier.padding(horizontal = 12.dp),
        label = {
            Text(
                text = workspace.data.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        icon = icon,
        selected = false,
        onClick = onClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun BasicDrawer() {
    val userContent = BasicDrawerContent.UserContent(
        user = User(
            id = Id("1"),
            data = UserData(
                name = "tuguzT",
                displayName = "Timur Tugushev",
                role = Role.User,
                email = "timurka.tugushev@gmail.com",
                avatarUrl = null,
            ),
        ),
        avatar = {
            Image(
                imageVector = Icons.Rounded.Person,
                contentDescription = stringResource(R.string.user_avatar),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape),
            )
        },
    )
    val workspacesContent = BasicDrawerContent.WorkspacesContent(
        workspaces = listOf(
            Workspace(
                id = Id("1"),
                data = WorkspaceData(
                    name = "First workspace",
                    description = "",
                    visibility = Visibility.Public,
                    imageUrl = null,
                ),
            ),
            Workspace(
                id = Id("2"),
                data = WorkspaceData(
                    name = "Second workspace",
                    description = "",
                    visibility = Visibility.Public,
                    imageUrl = null,
                ),
            ),
        ),
        icon = { Icon(Icons.Rounded.Groups3, contentDescription = null) },
    )
    val drawerContent = BasicDrawerContent(
        user = userContent,
        workspaces = workspacesContent,
    )

    AppTheme {
        BasicDrawer(
            drawerContent = drawerContent,
            drawerState = rememberDrawerState(DrawerValue.Open),
            onUserClick = {},
            onSettingsClick = {},
            onAboutClick = {},
            onWorkspaceClick = {},
            content = {},
        )
    }
}
