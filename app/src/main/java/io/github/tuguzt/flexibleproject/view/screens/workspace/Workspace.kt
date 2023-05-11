package io.github.tuguzt.flexibleproject.view.screens.workspace

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.viewmodel.workspace.WorkspaceViewModel
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore.Intent

@RootNavGraph
@Destination
@Composable
fun WorkspaceScreen(
    id: String,
    navigator: DestinationsNavigator,
    viewModel: WorkspaceViewModel = hiltViewModel(),
) {
    val state by viewModel.stateFlow.collectAsState()
    LaunchedEffect(id) {
        val intent = Intent.Load(id = WorkspaceId(id))
        viewModel.accept(intent)
    }

    Scaffold(
        topBar = {
            WorkspaceTopBar(
                workspace = state.workspace,
                onNavigationClick = navigator::navigateUp,
            )
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding))
    }
}
