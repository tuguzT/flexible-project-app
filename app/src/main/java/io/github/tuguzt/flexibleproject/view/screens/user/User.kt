package io.github.tuguzt.flexibleproject.view.screens.user

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.view.utils.collectInLaunchedEffectWithLifecycle
import io.github.tuguzt.flexibleproject.viewmodel.user.UserViewModel
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.Intent

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun UserScreen(
    id: String,
    navigator: DestinationsNavigator,
    viewModel: UserViewModel = hiltViewModel(),
) {
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(id) {
        val intent = Intent.Load(id = UserId(id))
        viewModel.accept(intent)
    }
    viewModel.labels.collectInLaunchedEffectWithLifecycle { label ->
        // TODO show error to user before navigate up
        when (label) {
            UserStore.Label.LocalStoreError -> navigator.navigateUp()
            UserStore.Label.NetworkAccessError -> navigator.navigateUp()
            is UserStore.Label.NotFound -> navigator.navigateUp()
            UserStore.Label.UnknownError -> navigator.navigateUp()
        }
    }

    UserScreenContent(
        user = state.user,
        onNavigationClick = navigator::navigateUp,
    )
}
