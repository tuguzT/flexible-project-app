package io.github.tuguzt.flexibleproject.view.screens.basic.home

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Groups3
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.tuguzt.flexibleproject.view.screens.basic.BasicNavGraph
import io.github.tuguzt.flexibleproject.view.screens.destinations.AboutScreenDestination
import io.github.tuguzt.flexibleproject.view.screens.destinations.AddWorkspaceScreenDestination
import io.github.tuguzt.flexibleproject.view.screens.destinations.HomeBottomSheetDestination
import io.github.tuguzt.flexibleproject.view.screens.destinations.SettingsScreenDestination
import io.github.tuguzt.flexibleproject.view.screens.destinations.UserScreenDestination
import io.github.tuguzt.flexibleproject.view.screens.destinations.WorkspaceScreenDestination
import io.github.tuguzt.flexibleproject.view.utils.UserAvatar
import io.github.tuguzt.flexibleproject.view.utils.WorkspaceImage
import io.github.tuguzt.flexibleproject.view.utils.collectInLaunchedEffectWithLifecycle
import io.github.tuguzt.flexibleproject.viewmodel.basic.home.HomeViewModel
import io.github.tuguzt.flexibleproject.viewmodel.basic.home.store.HomeStore
import io.github.tuguzt.flexibleproject.viewmodel.basic.user.CurrentUserViewModel
import kotlinx.coroutines.launch

@BasicNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    currentUserViewModel: CurrentUserViewModel,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val currentUserState by currentUserViewModel.stateFlow.collectAsStateWithLifecycle()
    val currentUser = currentUserState.currentUser

    homeViewModel.labels.collectInLaunchedEffectWithLifecycle { label ->
        when (label) {
            HomeStore.Label.LocalStoreError -> TODO("show error to user")
            HomeStore.Label.NetworkAccessError -> TODO("show error to user")
            HomeStore.Label.UnknownError -> TODO("show error to user")
        }
    }
    val basicState by homeViewModel.stateFlow.collectAsStateWithLifecycle()

    val userContent = HomeContent.UserContent(
        user = currentUser,
        avatar = {
            UserAvatar(
                user = currentUser,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape),
                error = { Icon(Icons.Rounded.Person, contentDescription = null) },
            )
        },
    )
    val workspacesContent = HomeContent.WorkspacesContent(
        workspaces = basicState.workspaces,
        icon = { workspace ->
            WorkspaceImage(
                workspace = workspace,
                modifier = Modifier.size(24.dp),
                error = { Icon(Icons.Rounded.Groups3, contentDescription = null) },
            )
        },
    )
    val content = HomeContent(
        user = userContent,
        workspaces = workspacesContent,
    )

    HomeContent(
        content = content,
        drawerState = drawerState,
        onDrawerUserClick = click@{
            val id = currentUser?.id ?: return@click
            val direction = UserScreenDestination(id.toString())
            navigator.navigate(direction)
            coroutineScope.launch { drawerState.close() }
        },
        onDrawerSettingsClick = {
            val direction = SettingsScreenDestination()
            navigator.navigate(direction)
            coroutineScope.launch { drawerState.close() }
        },
        onDrawerAboutClick = {
            val direction = AboutScreenDestination()
            navigator.navigate(direction)
            coroutineScope.launch { drawerState.close() }
        },
        drawerWorkspacesExpanded = basicState.workspacesExpanded,
        onDrawerWorkspacesExpandedChange = {
            val intent = HomeStore.Intent.WorkspacesExpand(it)
            homeViewModel.accept(intent)
        },
        onWorkspaceClick = { workspace ->
            val id = workspace.id
            val direction = WorkspaceScreenDestination(id.toString())
            navigator.navigate(direction)
            coroutineScope.launch { drawerState.close() }
        },
        onDrawerAddNewWorkspaceClick = {
            val direction = AddWorkspaceScreenDestination()
            navigator.navigate(direction)
            coroutineScope.launch { drawerState.close() }
        },
        onMenuClick = {
            coroutineScope.launch { drawerState.open() }
        },
        onAddClick = {
            val direction = HomeBottomSheetDestination()
            navigator.navigate(direction)
        },
    )
}
