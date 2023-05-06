package io.github.tuguzt.flexibleproject.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Groups3
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ramcosta.composedestinations.DestinationsNavHost
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
@Composable
fun AppRoot() {
    val engine = rememberNavHostEngine()
    val navController = engine.rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: HomeScreenDestination.route
    println(currentRoute)

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val userState = AppNavigationDrawerState.UserState(
        user = User(
            name = "tuguzT",
            displayName = "Timur Tugushev",
            role = Role.User,
            email = null,
        ),
        avatar = {
            Image(
                painter = rememberVectorPainter(Icons.Rounded.Person),
                contentDescription = stringResource(R.string.user_avatar),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape),
            )
        },
    )
    val workspaceState = AppNavigationDrawerState.WorkspaceState(
        workspace = Workspace(
            id = "1",
            name = "Empty workspace",
        ),
        icon = { Icon(Icons.Rounded.Groups3, contentDescription = null) },
    )

    AppNavigationDrawer(
        state = AppNavigationDrawerState(
            currentRoute = currentRoute,
            userState = userState,
            workspacesData = listOf(workspaceState),
        ),
        onHomeDestinationClick = {
            val destination = HomeScreenDestination()
            navController.navigate(destination) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            coroutineScope.launch { drawerState.close() }
        },
        onSettingsDestinationClick = {
            val destination = SettingsScreenDestination()
            navController.navigate(destination) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            coroutineScope.launch { drawerState.close() }
        },
        onWorkspaceDestinationClick = { workspace ->
            val destination = WorkspaceScreenDestination(workspace.id)
            navController.navigate(destination) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            coroutineScope.launch { drawerState.close() }
        },
        drawerState = drawerState,
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = "Flexible Project") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch { drawerState.open() }
                            },
                        ) {
                            Icon(Icons.Rounded.Menu, contentDescription = null)
                        }
                    }
                )
            },
        ) {
            DestinationsNavHost(
                engine = engine,
                navController = navController,
                navGraph = NavGraphs.root,
                modifier = Modifier.padding(it),
            )
        }
    }
}
