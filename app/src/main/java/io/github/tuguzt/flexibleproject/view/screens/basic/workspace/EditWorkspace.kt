package io.github.tuguzt.flexibleproject.view.screens.basic.workspace

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
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.UpdateWorkspaceViewModel
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.UpdateWorkspaceStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.UpdateWorkspaceStore.Label

@BasicNavGraph
@Destination
@Composable
fun EditWorkspaceScreen(
    id: String,
    navigator: DestinationsNavigator,
    viewModel: UpdateWorkspaceViewModel = hiltViewModel(),
) {
    LaunchedEffect(id) {
        val intent = Intent.Load(WorkspaceId(id))
        viewModel.accept(intent)
    }

    viewModel.labels.collectInLaunchedEffectWithLifecycle { label ->
        // TODO show error to user before navigate up
        when (label) {
            is Label.NotFound -> navigator.navigateUp()
            Label.LocalStoreError -> navigator.navigateUp()
            Label.NetworkAccessError -> navigator.navigateUp()
            Label.UnknownError -> navigator.navigateUp()
            Label.WorkspaceUpdated -> navigator.navigateUp()
        }
    }
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EditWorkspaceContent(
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
        image = state.image.orEmpty(),
        onImageChange = { image ->
            val intent = Intent.ChangeImage(image.takeIf(String::isNotBlank))
            viewModel.accept(intent)
        },
        loading = state.loading,
        valid = state.valid,
        onSubmit = {
            val intent = Intent.UpdateWorkspace
            viewModel.accept(intent)
        },
        onNavigationClick = navigator::navigateUp,
    )
}
