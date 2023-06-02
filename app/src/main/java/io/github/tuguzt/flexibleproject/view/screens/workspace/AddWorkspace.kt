package io.github.tuguzt.flexibleproject.view.screens.workspace

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.tuguzt.flexibleproject.view.utils.collectInLaunchedEffectWithLifecycle
import io.github.tuguzt.flexibleproject.viewmodel.workspace.AddWorkspaceViewModel
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.AddWorkspaceStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.AddWorkspaceStore.Label

@RootNavGraph
@Destination
@Composable
fun AddWorkspaceScreen(
    navigator: DestinationsNavigator,
    viewModel: AddWorkspaceViewModel = hiltViewModel(),
) {
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
    viewModel.labels.collectInLaunchedEffectWithLifecycle { label ->
        when (label) {
            // TODO show message for user before navigate up
            is Label.WorkspaceCreated -> navigator.navigateUp()
            Label.LocalStoreError -> navigator.navigateUp()
            Label.NetworkAccessError -> navigator.navigateUp()
            Label.UnknownError -> navigator.navigateUp()
        }
    }

    AddWorkspaceContent(
        name = state.name,
        onNameChange = { name ->
            val intent = Intent.ChangeName(name)
            viewModel.accept(intent)
        },
        description = state.description,
        onDescriptionChange = { description ->
            val intent = Intent.ChangeDescription(description)
            viewModel.accept(intent)
        },
        visibility = state.visibility,
        onVisibilityChange = { visibility ->
            val intent = Intent.ChangeVisibility(visibility)
            viewModel.accept(intent)
        },
        loading = state.loading,
        valid = state.valid,
        onAddWorkspaceClick = {
            val intent = Intent.CreateWorkspace
            viewModel.accept(intent)
        },
        onNavigationClick = navigator::navigateUp,
    )
}
