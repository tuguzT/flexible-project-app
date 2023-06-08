package io.github.tuguzt.flexibleproject.view

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.rememberNavHostEngine
import io.github.tuguzt.flexibleproject.domain.model.settings.Theme
import io.github.tuguzt.flexibleproject.view.screens.NavGraphs
import io.github.tuguzt.flexibleproject.view.screens.destinations.AuthScreenDestination
import io.github.tuguzt.flexibleproject.view.screens.destinations.BasicScreenDestination
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.collectInLaunchedEffectWithLifecycle
import io.github.tuguzt.flexibleproject.viewmodel.settings.SettingsViewModel
import io.github.tuguzt.flexibleproject.viewmodel.user.CurrentUserViewModel
import io.github.tuguzt.flexibleproject.viewmodel.user.store.CurrentUserStore

@Composable
fun MainActivityContent(
    currentUserViewModel: CurrentUserViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    val engine = rememberNavHostEngine()
    val navController = engine.rememberNavController()

    currentUserViewModel.labels.collectInLaunchedEffectWithLifecycle block@{ label ->
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

    val settingsState by settingsViewModel.stateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(settingsState.language) {
        // TODO change application language
    }
    val darkTheme = when (settingsState.theme) {
        Theme.System -> isSystemInDarkTheme()
        Theme.Light -> false
        Theme.Dark -> true
    }

    val navGraph = NavGraphs.root
    val startRoute = run {
        val state = currentUserViewModel.stateFlow.value
        if (state.currentUser == null) {
            AuthScreenDestination
        } else {
            navGraph.startRoute
        }
    }

    AppTheme(darkTheme) {
        Surface(modifier = Modifier.fillMaxSize()) {
            DestinationsNavHost(
                navGraph = navGraph,
                startRoute = startRoute,
                engine = engine,
                navController = navController,
                dependenciesContainerBuilder = {
                    dependency(currentUserViewModel)
                    dependency(settingsViewModel)
                },
            )
        }
    }
}
