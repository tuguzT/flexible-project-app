package io.github.tuguzt.flexibleproject.view.screens.basic.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.view.screens.basic.BasicNavGraph
import io.github.tuguzt.flexibleproject.view.utils.collectInLaunchedEffectWithLifecycle
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.AddProjectViewModel
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.AddProjectStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.AddProjectStore.Label

@BasicNavGraph
@Destination
@Composable
fun AddProject(
    workspaceId: String? = null,
    navigator: DestinationsNavigator,
    viewModel: AddProjectViewModel = hiltViewModel(),
) {
    LaunchedEffect(workspaceId) {
        workspaceId ?: return@LaunchedEffect
        val intent = Intent.ChangeWorkspaceFromId(id = WorkspaceId(workspaceId))
        viewModel.accept(intent)
    }

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
    viewModel.labels.collectInLaunchedEffectWithLifecycle { label ->
        // TODO show message for user before navigate up
        when (label) {
            is Label.ProjectCreated -> navigator.navigateUp()
            Label.LocalStoreError -> navigator.navigateUp()
            Label.NetworkAccessError -> navigator.navigateUp()
            Label.UnknownError -> navigator.navigateUp()
        }
    }

    AddProjectContent(
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
        allWorkspaces = state.allWorkspaces,
        workspace = state.workspace,
        onWorkspaceChange = { workspace ->
            val intent = Intent.ChangeWorkspace(workspace)
            viewModel.accept(intent)
        },
        loading = state.loading,
        valid = state.valid,
        onAddProjectClick = {
            val intent = Intent.CreateProject
            viewModel.accept(intent)
        },
        onNavigationClick = navigator::navigateUp,
    )
}
