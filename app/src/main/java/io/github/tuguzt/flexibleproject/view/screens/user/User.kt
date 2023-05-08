package io.github.tuguzt.flexibleproject.view.screens.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.tuguzt.flexibleproject.viewmodel.user.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun UserScreen(
    navigator: DestinationsNavigator,
    viewModel: UserViewModel,
) {
    val state by viewModel.stateFlow.collectAsState()

    Scaffold(
        topBar = {
            UserTopBar(
                user = state.user ?: return@Scaffold,
                onNavigationClick = navigator::navigateUp,
            )
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding))
    }
}
