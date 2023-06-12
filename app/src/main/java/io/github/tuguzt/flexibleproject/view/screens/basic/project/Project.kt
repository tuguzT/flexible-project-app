package io.github.tuguzt.flexibleproject.view.screens.basic.project

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectId
import io.github.tuguzt.flexibleproject.view.screens.basic.BasicNavGraph
import io.github.tuguzt.flexibleproject.view.utils.collectInLaunchedEffectWithLifecycle
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.ProjectViewModel
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.ProjectStore
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.ProjectStore.Label

@OptIn(ExperimentalMaterial3Api::class)
@BasicNavGraph
@Destination
@Composable
fun ProjectScreen(
    id: String,
    navigator: DestinationsNavigator,
    viewModel: ProjectViewModel = hiltViewModel(),
) {
    LaunchedEffect(id) {
        val intent = ProjectStore.Intent.Load(id = ProjectId(id))
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

    ProjectContent(
        project = state.project,
        loading = state.loading,
        onNavigationClick = navigator::navigateUp,
        topBarActions = { /* TODO */ },
    )
}
