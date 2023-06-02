package io.github.tuguzt.flexibleproject.view.screens.workspace

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.view.screens.destinations.EditWorkspaceScreenDestination
import io.github.tuguzt.flexibleproject.view.utils.collectInLaunchedEffectWithLifecycle
import io.github.tuguzt.flexibleproject.viewmodel.workspace.WorkspaceViewModel
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore.Label

@RootNavGraph
@Destination
@Composable
fun WorkspaceScreen(
    id: String,
    navigator: DestinationsNavigator,
    viewModel: WorkspaceViewModel = hiltViewModel(),
) {
    LaunchedEffect(id) {
        val intent = Intent.Load(id = WorkspaceId(id))
        viewModel.accept(intent)
    }

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
    viewModel.labels.collectInLaunchedEffectWithLifecycle { label ->
        // TODO show error to user before navigate up
        when (label) {
            Label.LocalStoreError -> navigator.navigateUp()
            Label.NetworkAccessError -> navigator.navigateUp()
            Label.UnknownError -> navigator.navigateUp()
            is Label.NotFound -> navigator.navigateUp()
            is Label.WorkspaceDeleted -> navigator.navigateUp()
        }
    }

    var expanded by remember { mutableStateOf(false) }

    WorkspaceContent(
        workspace = state.workspace,
        loading = state.loading,
        onNavigationClick = navigator::navigateUp,
        topBarActions = {
            WorkspaceActions(
                enabled = !state.loading,
                onShareClick = { /* TODO */ },
                menuExpanded = expanded,
                onMenuExpandedChange = { expanded = it },
                onEditClick = {
                    expanded = false
                    val direction = EditWorkspaceScreenDestination()
                    navigator.navigate(direction)
                },
                onDeleteClick = {
                    expanded = false
                    val intent = Intent.Delete
                    viewModel.accept(intent)
                },
            )
        },
    )
}
