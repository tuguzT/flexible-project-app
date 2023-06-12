package io.github.tuguzt.flexibleproject.view.screens.basic.project

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectId
import io.github.tuguzt.flexibleproject.view.screens.basic.BasicNavGraph
import io.github.tuguzt.flexibleproject.view.screens.destinations.DeleteProjectDestination
import io.github.tuguzt.flexibleproject.view.utils.collectInLaunchedEffectWithLifecycle
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.ProjectViewModel
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.ProjectStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.ProjectStore.Label

@OptIn(ExperimentalMaterial3Api::class)
@BasicNavGraph
@Destination
@Composable
fun ProjectScreen(
    id: String,
    navigator: DestinationsNavigator,
    deleteRecipient: ResultRecipient<DeleteProjectDestination, Boolean>,
    viewModel: ProjectViewModel = hiltViewModel(),
) {
    LaunchedEffect(id) {
        val intent = Intent.Load(id = ProjectId(id))
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
            is Label.ProjectDeleted -> navigator.navigateUp()
        }
    }

    var expanded by remember { mutableStateOf(false) }

    ProjectContent(
        project = state.project,
        loading = state.loading,
        onNavigationClick = navigator::navigateUp,
        topBarActions = {
            ProjectActions(
                enabled = !state.loading,
                onShareClick = { /*TODO*/ },
                menuExpanded = expanded,
                onMenuExpandedChange = { expanded = it },
                onEditClick = block@{
                    expanded = false
                    val project = state.project ?: return@block
                    // TODO
                },
                onDeleteClick = {
                    expanded = false
                    val direction = DeleteProjectDestination()
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
