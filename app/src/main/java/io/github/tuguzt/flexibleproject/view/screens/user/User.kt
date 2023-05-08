package io.github.tuguzt.flexibleproject.view.screens.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.viewmodel.user.UserViewModel
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
    val state by viewModel.stateFlow.collectAsState()
    LaunchedEffect(id) {
        val intent = Intent.Load(id = UserId(id))
        viewModel.accept(intent)
    }

    Scaffold(
        topBar = {
            UserTopBar(
                user = state.user,
                onNavigationClick = navigator::navigateUp,
            )
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding))
    }
}
