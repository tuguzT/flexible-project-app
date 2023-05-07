package io.github.tuguzt.flexibleproject.view.screens.basic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Groups3
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.rememberNavHostEngine
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.model.user.Role
import io.github.tuguzt.flexibleproject.model.user.User
import io.github.tuguzt.flexibleproject.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.view.screens.NavGraphs
import io.github.tuguzt.flexibleproject.view.screens.destinations.HomeScreenDestination
import io.github.tuguzt.flexibleproject.view.screens.destinations.SettingsScreenDestination
import io.github.tuguzt.flexibleproject.view.screens.destinations.WorkspaceScreenDestination
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun BasicScreen(
    navigator: DestinationsNavigator,
) {
    val engine = rememberNavHostEngine()
    val navController = engine.rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: HomeScreenDestination.route

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val userContent = BasicDrawerContent.UserContent(
        user = User(
            name = "tuguzT",
            displayName = "Timur Tugushev",
            role = Role.User,
            email = null,
        ),
        avatar = { // TODO get from avatar url
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
                id = "1",
                name = "First workspace",
            ),
            Workspace(
                id = "2",
                name = "Second workspace",
            )
        ),
        icon = { // TODO get from workspace icon url
            Icon(Icons.Rounded.Groups3, contentDescription = null)
        },
    )
    val drawerContent = BasicDrawerContent(
        currentRoute = currentRoute,
        user = userContent,
        workspaces = workspacesContent,
    )

    BasicDrawer(
        drawerContent = drawerContent,
        drawerState = drawerState,
        onHomeClick = {
            val direction = HomeScreenDestination()
            navController.navigate(direction) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            coroutineScope.launch { drawerState.close() }
        },
        onSettingsClick = {
            val direction = SettingsScreenDestination()
            navController.navigate(direction) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            coroutineScope.launch { drawerState.close() }
        },
        onWorkspaceClick = { workspace ->
            val direction = WorkspaceScreenDestination(workspace.id)
            navigator.navigate(direction)
            coroutineScope.launch { drawerState.close() }
        },
    ) {
        Scaffold(
            topBar = {
                val onMenuClick: () -> Unit = {
                    coroutineScope.launch { drawerState.open() }
                }
                BasicTopBar(onMenuClick = onMenuClick)
            }
        ) { padding ->
            DestinationsNavHost(
                engine = engine,
                navController = navController,
                navGraph = NavGraphs.basic,
                modifier = Modifier.padding(padding),
            )
        }
    }
}
