package io.github.tuguzt.flexibleproject.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.rememberNavHostEngine
import io.github.tuguzt.flexibleproject.view.screens.NavGraphs
import io.github.tuguzt.flexibleproject.view.screens.destinations.AuthScreenDestination
import io.github.tuguzt.flexibleproject.view.screens.destinations.BasicScreenDestination
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.collectInLaunchedEffectWithLifecycle
import io.github.tuguzt.flexibleproject.viewmodel.user.CurrentUserViewModel
import io.github.tuguzt.flexibleproject.viewmodel.user.store.CurrentUserStore

@Composable
fun MainActivityContent(
    viewModel: CurrentUserViewModel = hiltViewModel(),
) {
    val engine = rememberNavHostEngine()
    val navController = engine.rememberNavController()

    viewModel.labels.collectInLaunchedEffectWithLifecycle block@{ label ->
        val direction = when (label) {
            is CurrentUserStore.Label.SignedInUp -> BasicScreenDestination()
            CurrentUserStore.Label.SignedOut -> AuthScreenDestination()
            else -> return@block
        }
        navController.navigate(direction) {
            popUpTo(NavGraphs.root) {
                inclusive = true
            }
        }
    }

    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val navGraph = NavGraphs.root
            val startRoute = run {
                val state = viewModel.stateFlow.value
                if (state.currentUser == null) {
                    AuthScreenDestination
                } else {
                    navGraph.startRoute
                }
            }

            DestinationsNavHost(
                navGraph = navGraph,
                startRoute = startRoute,
                engine = engine,
                navController = navController,
                dependenciesContainerBuilder = {
                    dependency(viewModel)
                },
            )
        }
    }
}
