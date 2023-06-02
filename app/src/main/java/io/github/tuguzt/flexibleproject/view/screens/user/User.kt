package io.github.tuguzt.flexibleproject.view.screens.user

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
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.view.screens.destinations.EditUserScreenDestination
import io.github.tuguzt.flexibleproject.view.utils.collectInLaunchedEffectWithLifecycle
import io.github.tuguzt.flexibleproject.viewmodel.user.CurrentUserViewModel
import io.github.tuguzt.flexibleproject.viewmodel.user.UserViewModel
import io.github.tuguzt.flexibleproject.viewmodel.user.store.CurrentUserStore
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.Intent

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun UserScreen(
    id: String,
    navigator: DestinationsNavigator,
    currentUserViewModel: CurrentUserViewModel,
    viewModel: UserViewModel = hiltViewModel(),
) {
    LaunchedEffect(id) {
        val intent = Intent.Load(id = UserId(id))
        viewModel.accept(intent)
    }

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
    viewModel.labels.collectInLaunchedEffectWithLifecycle { label ->
        // TODO show error to user before navigate up
        when (label) {
            UserStore.Label.LocalStoreError -> navigator.navigateUp()
            UserStore.Label.NetworkAccessError -> navigator.navigateUp()
            is UserStore.Label.NotFound -> navigator.navigateUp()
            UserStore.Label.UnknownError -> navigator.navigateUp()
        }
    }

    val currentUserState by currentUserViewModel.stateFlow.collectAsStateWithLifecycle()

    var expanded by remember { mutableStateOf(false) }
    val onEditClick = run {
        val onClick = {
            expanded = false
            val direction = EditUserScreenDestination()
            navigator.navigate(direction)
        }
        currentUserState.currentUser?.let { onClick }
    }
    val onSignOutClick = run {
        val onClick = {
            expanded = false
            val intent = CurrentUserStore.Intent.SignOut
            currentUserViewModel.accept(intent)
        }
        currentUserState.currentUser?.let { onClick }
    }
    val onDeleteClick = run {
        val onClick = {
            expanded = false
            val intent = CurrentUserStore.Intent.Delete
            currentUserViewModel.accept(intent)
        }
        currentUserState.currentUser?.let { onClick }
    }

    UserContent(
        user = state.user,
        loading = state.loading || currentUserState.loading,
        onWorkspacesClick = { /* TODO */ },
        onProjectsClick = { /* TODO */ },
        onTasksClick = { /* TODO */ },
        onMethodologiesClick = { /* TODO */ },
        onNavigationClick = navigator::navigateUp,
        topBarActions = {
            UserActions(
                enabled = !state.loading && !currentUserState.loading,
                onShareClick = { /* TODO */ },
                menuExpanded = expanded,
                onMenuExpandedChange = { expanded = it },
                onEditClick = onEditClick,
                onSignOutClick = onSignOutClick,
                onDeleteClick = onDeleteClick,
            )
        },
    )
}
