package io.github.tuguzt.flexibleproject.view.screens.basic.workspace

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.view.screens.basic.BasicNavGraph
import io.github.tuguzt.flexibleproject.view.screens.destinations.DeleteWorkspaceDestination
import io.github.tuguzt.flexibleproject.view.screens.destinations.EditWorkspaceScreenDestination
import io.github.tuguzt.flexibleproject.view.utils.ImageByUrl
import io.github.tuguzt.flexibleproject.view.utils.ImageError
import io.github.tuguzt.flexibleproject.view.utils.collectInLaunchedEffectWithLifecycle
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.WorkspaceViewModel
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.WorkspaceStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.WorkspaceStore.Label

@OptIn(ExperimentalMaterial3Api::class)
@BasicNavGraph
@Destination
@Composable
fun WorkspaceScreen(
    id: String,
    navigator: DestinationsNavigator,
    deleteRecipient: ResultRecipient<DeleteWorkspaceDestination, Boolean>,
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
        onProjectClick = {
            // TODO navigate to project screen
        },
        projectImage = { project ->
            ImageByUrl(
                url = project.data.image,
                modifier = Modifier.size(96.dp),
                error = {
                    ImageError(imageVector = Icons.Rounded.Dashboard, imageSize = 48.dp)
                },
            )
        },
        topBarActions = {
            WorkspaceActions(
                enabled = !state.loading,
                onShareClick = { /* TODO */ },
                menuExpanded = expanded,
                onMenuExpandedChange = { expanded = it },
                onEditClick = block@{
                    expanded = false
                    val workspace = state.workspace?.workspace ?: return@block
                    val direction = EditWorkspaceScreenDestination(workspace.id.toString())
                    navigator.navigate(direction)
                },
                onDeleteClick = {
                    expanded = false
                    val direction = DeleteWorkspaceDestination()
                    navigator.navigate(direction)
                },
            )
        },
    )

    deleteRecipient.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> Unit
            is NavResult.Value -> {
                if (!result.value) return@onNavResult
                val intent = Intent.Delete
                viewModel.accept(intent)
            }
        }
    }
}
