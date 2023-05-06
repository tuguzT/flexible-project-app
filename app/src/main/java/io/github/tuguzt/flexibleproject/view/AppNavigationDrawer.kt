package io.github.tuguzt.flexibleproject.view

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.rounded.Home
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.model.user.Role
import io.github.tuguzt.flexibleproject.model.user.User
import io.github.tuguzt.flexibleproject.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.view.theme.AppTheme

data class AppNavigationDrawerData(
    val user: AppNavigationDrawerUser,
    val workspaces: List<AppNavigationDrawerWorkspace>,
)

data class AppNavigationDrawerUser(
    val user: User,
    val avatarPainter: Painter,
)

data class AppNavigationDrawerWorkspace(
    val workspace: Workspace,
    val imagePainter: Painter,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationDrawer(
    data: AppNavigationDrawerData,
    onHomeDestinationClick: () -> Unit,
    onSettingsDestinationClick: () -> Unit,
    onWorkspaceDestinationClick: (Workspace) -> Unit,
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                val (user, userAvatarPainter) = data.user
                UserData(user = user, avatarPainter = userAvatarPainter)

                Spacer(modifier = Modifier.height(8.dp))
                HomeDrawerItem(
                    selected = false, // TODO find a way to get current destination
                    onClick = onHomeDestinationClick,
                )
                SettingsDrawerItem(
                    selected = false, // TODO find a way to get current destination
                    onClick = onSettingsDestinationClick,
                )
                // TODO misc (idk what)
                Spacer(modifier = Modifier.height(8.dp))
                Divider(modifier = Modifier.padding(horizontal = 28.dp))

                Workspaces(
                    workspaces = data.workspaces,
                    onWorkspaceItemClick = onWorkspaceDestinationClick,
                )
            }
        },
        content = content,
    )
}

@Composable
private fun UserData(user: User, avatarPainter: Painter) {
    Surface(tonalElevation = 12.dp, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(horizontal = 28.dp, vertical = 16.dp)) {
            Image(
                painter = avatarPainter,
                contentDescription = stringResource(R.string.user_avatar),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape),
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = user.displayName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "@${user.name}", maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeDrawerItem(selected: Boolean, onClick: () -> Unit) {
    NavigationDrawerItem(
        modifier = Modifier.padding(horizontal = 12.dp),
        label = { Text(text = stringResource(R.string.home)) },
        icon = { Icon(Icons.Rounded.Home, contentDescription = null) },
        selected = selected,
        onClick = onClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsDrawerItem(selected: Boolean, onClick: () -> Unit) {
    NavigationDrawerItem(
        modifier = Modifier.padding(horizontal = 12.dp),
        label = { Text(text = stringResource(R.string.settings)) },
        icon = { Icon(Icons.Rounded.Settings, contentDescription = null) },
        selected = selected,
        onClick = onClick,
    )
}

@Composable
private fun Workspaces(
    workspaces: List<AppNavigationDrawerWorkspace>,
    onWorkspaceItemClick: (Workspace) -> Unit,
) {
    Column {
        Text(
            text = stringResource(R.string.workspaces),
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 16.dp),
        )
        LazyColumn {
            items(workspaces) { data ->
                val (workspace, imagePainter) = data
                WorkspaceDrawerItem(
                    workspace = workspace,
                    imagePainter = imagePainter,
                    selected = false, // TODO find a way to get current destination
                    onClick = { onWorkspaceItemClick(workspace) },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WorkspaceDrawerItem(
    workspace: Workspace,
    imagePainter: Painter,
    selected: Boolean,
    onClick: () -> Unit,
) {
    NavigationDrawerItem(
        modifier = Modifier.padding(horizontal = 12.dp),
        label = { Text(text = workspace.name, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        icon = { Icon(painter = imagePainter, contentDescription = null) },
        selected = selected,
        onClick = onClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AppNavigationDrawerPreview() {
    val data = AppNavigationDrawerData(
        user = AppNavigationDrawerUser(
            user = User(
                name = "tuguzT",
                displayName = "Timur Tugushev",
                role = Role.User,
                email = null,
            ),
            avatarPainter = rememberVectorPainter(Icons.Rounded.Person),
        ),
        workspaces = listOf(
            AppNavigationDrawerWorkspace(
                workspace = Workspace(
                    id = "1",
                    name = "Empty workspace",
                ),
                imagePainter = rememberVectorPainter(Icons.Rounded.Groups3)
            )
        ),
    )
    AppTheme {
        AppNavigationDrawer(
            data = data,
            onHomeDestinationClick = {},
            onSettingsDestinationClick = {},
            onWorkspaceDestinationClick = {},
            drawerState = rememberDrawerState(DrawerValue.Open),
            content = {},
        )
    }
}
