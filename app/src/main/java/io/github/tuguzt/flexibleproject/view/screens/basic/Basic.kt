package io.github.tuguzt.flexibleproject.view.screens.basic

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Groups3
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.rememberNavHostEngine
import io.github.tuguzt.flexibleproject.view.screens.NavGraphs
import io.github.tuguzt.flexibleproject.view.screens.destinations.AboutScreenDestination
import io.github.tuguzt.flexibleproject.view.screens.destinations.AddWorkspaceScreenDestination
import io.github.tuguzt.flexibleproject.view.screens.destinations.SettingsScreenDestination
import io.github.tuguzt.flexibleproject.view.screens.destinations.UserScreenDestination
import io.github.tuguzt.flexibleproject.view.screens.destinations.WorkspaceScreenDestination
import io.github.tuguzt.flexibleproject.view.utils.UserAvatar
import io.github.tuguzt.flexibleproject.view.utils.WorkspaceImage
import io.github.tuguzt.flexibleproject.viewmodel.auth.AuthViewModel
import io.github.tuguzt.flexibleproject.viewmodel.basic.BasicViewModel
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStore
import io.github.tuguzt.flexibleproject.viewmodel.user.UserViewModel
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore
import kotlinx.coroutines.launch

@RootNavGraph(start = true)
@Destination
@Composable
fun BasicScreen(
    navigator: DestinationsNavigator,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel = hiltViewModel(),
    basicViewModel: BasicViewModel = hiltViewModel(),
) {
    val engine = rememberNavHostEngine()
    val navController = engine.rememberNavController()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val authState by authViewModel.stateFlow.collectAsStateWithLifecycle()
    val userState by userViewModel.stateFlow.collectAsStateWithLifecycle()
    val basicState by basicViewModel.stateFlow.collectAsStateWithLifecycle()

    val currentUser = authState.currentUser ?: return // TODO navigate to the auth flow
    LaunchedEffect(currentUser) {
        val intent = UserStore.Intent.Load(currentUser.id)
        userViewModel.accept(intent)
    }
    LaunchedEffect(Unit) {
        val intent = BasicStore.Intent.Load
        basicViewModel.accept(intent)
    }

    val userContent = BasicDrawerContent.UserContent(
        user = userState.user,
        avatar = {
            UserAvatar(
                user = userState.user,
                modifier = Modifier.size(72.dp).clip(CircleShape),
                error = { Icon(Icons.Rounded.Person, contentDescription = null) },
            )
        },
    )
    val workspacesContent = BasicDrawerContent.WorkspacesContent(
        workspaces = basicState.workspaces,
        icon = { workspace ->
            WorkspaceImage(
                workspace = workspace,
                modifier = Modifier.size(24.dp),
                error = { Icon(Icons.Rounded.Groups3, contentDescription = null) },
            )
        },
    )
    val drawerContent = BasicDrawerContent(
        user = userContent,
        workspaces = workspacesContent,
    )

    BasicDrawer(
        drawerContent = drawerContent,
        drawerState = drawerState,
        onUserClick = click@{
            val id = userState.user?.id
            val direction = UserScreenDestination(id.toString())
            navigator.navigate(direction)
            coroutineScope.launch { drawerState.close() }
        },
        onSettingsClick = {
            val direction = SettingsScreenDestination()
            navigator.navigate(direction)
            coroutineScope.launch { drawerState.close() }
        },
        onAboutClick = {
            val direction = AboutScreenDestination()
            navigator.navigate(direction)
            coroutineScope.launch { drawerState.close() }
        },
        workspacesExpanded = basicState.workspacesExpanded,
        onWorkspacesExpandedChange = {
            val intent = BasicStore.Intent.WorkspacesExpand(it)
            basicViewModel.accept(intent)
        },
        onWorkspaceClick = { workspace ->
            val id = workspace.id
            val direction = WorkspaceScreenDestination(id.toString())
            navigator.navigate(direction)
            coroutineScope.launch { drawerState.close() }
        },
        onAddNewWorkspaceClick = {
            val direction = AddWorkspaceScreenDestination()
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
            },
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
