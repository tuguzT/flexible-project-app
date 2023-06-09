package io.github.tuguzt.flexibleproject.view.screens.basic

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.tuguzt.flexibleproject.view.screens.destinations.AboutScreenDestination
import io.github.tuguzt.flexibleproject.view.screens.destinations.AddWorkspaceScreenDestination
import io.github.tuguzt.flexibleproject.view.screens.destinations.SettingsScreenDestination
import io.github.tuguzt.flexibleproject.view.screens.destinations.UserScreenDestination
import io.github.tuguzt.flexibleproject.view.screens.destinations.WorkspaceScreenDestination
import io.github.tuguzt.flexibleproject.view.utils.UserAvatar
import io.github.tuguzt.flexibleproject.view.utils.WorkspaceImage
import io.github.tuguzt.flexibleproject.view.utils.collectInLaunchedEffectWithLifecycle
import io.github.tuguzt.flexibleproject.viewmodel.basic.BasicViewModel
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStore
import io.github.tuguzt.flexibleproject.viewmodel.user.CurrentUserViewModel
import kotlinx.coroutines.launch

@RootNavGraph(start = true)
@Destination
@Composable
fun BasicScreen(
    navigator: DestinationsNavigator,
    currentUserViewModel: CurrentUserViewModel,
    basicViewModel: BasicViewModel = hiltViewModel(),
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val currentUserState by currentUserViewModel.stateFlow.collectAsStateWithLifecycle()
    val currentUser = currentUserState.currentUser

    basicViewModel.labels.collectInLaunchedEffectWithLifecycle { label ->
        when (label) {
            BasicStore.Label.LocalStoreError -> TODO("show error to user")
            BasicStore.Label.NetworkAccessError -> TODO("show error to user")
            BasicStore.Label.UnknownError -> TODO("show error to user")
        }
    }
    val basicState by basicViewModel.stateFlow.collectAsStateWithLifecycle()

    val userContent = BasicContent.UserContent(
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
    val workspacesContent = BasicContent.WorkspacesContent(
        workspaces = basicState.workspaces,
        icon = { workspace ->
            WorkspaceImage(
                workspace = workspace,
                modifier = Modifier.size(24.dp),
                error = { Icon(Icons.Rounded.Groups3, contentDescription = null) },
            )
        },
    )
    val content = BasicContent(
        user = userContent,
        workspaces = workspacesContent,
    )

    var sheetExpanded by remember { mutableStateOf(false) }

    BasicContent(
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
            val intent = BasicStore.Intent.WorkspacesExpand(it)
            basicViewModel.accept(intent)
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
        onAddClick = { sheetExpanded = true },
        sheetExpanded = sheetExpanded,
        onSheetExpandedChange = { sheetExpanded = it },
        onAddWorkspaceClick = {
            sheetExpanded = false
            val direction = AddWorkspaceScreenDestination()
            navigator.navigate(direction)
        },
        onAddProjectClick = {
            sheetExpanded = false
            // TODO
        },
        onAddMethodologyClick = {
            sheetExpanded = false
            // TODO
        },
    )
}
