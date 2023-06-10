package io.github.tuguzt.flexibleproject.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import io.github.tuguzt.flexibleproject.domain.model.settings.Theme
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.view.screens.NavGraphs
import io.github.tuguzt.flexibleproject.view.screens.destinations.AuthScreenDestination
import io.github.tuguzt.flexibleproject.view.screens.destinations.BasicScreenDestination
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.collectInLaunchedEffectWithLifecycle
import io.github.tuguzt.flexibleproject.view.utils.setLocale
import io.github.tuguzt.flexibleproject.viewmodel.settings.SettingsViewModel
import io.github.tuguzt.flexibleproject.viewmodel.user.CurrentUserViewModel
import io.github.tuguzt.flexibleproject.viewmodel.user.store.CurrentUserStore

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun MainActivityContent(
    currentUserViewModel: CurrentUserViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    val engine = rememberAnimatedNavHostEngine(
        rootDefaultAnimations = RootNavGraphDefaultAnimations(
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 300)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 300)) },
        ),
    )
    val navController = engine.rememberNavController()

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator

    var prevUserId: UserId? by remember { mutableStateOf(null) }
    currentUserViewModel.labels.collectInLaunchedEffectWithLifecycle block@{ label ->
        val direction = when (label) {
            is CurrentUserStore.Label.SignedInUp -> {
                val userId = label.currentUser.id
                if (prevUserId == userId) return@block
                prevUserId = userId
                BasicScreenDestination()
            }

            CurrentUserStore.Label.SignedOut -> {
                prevUserId = null
                AuthScreenDestination()
            }

            else -> return@block
        }
        navController.navigate(direction) {
            popUpTo(NavGraphs.root) {
                inclusive = true
            }
        }
    }

    val context = LocalContext.current
    val settingsState by settingsViewModel.stateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(settingsState.language) {
        context.setLocale(settingsState.language)
    }
    val darkTheme = run {
        val systemInDarkTheme = isSystemInDarkTheme()
        remember(settingsState.theme, systemInDarkTheme) {
            when (settingsState.theme) {
                Theme.System -> systemInDarkTheme
                Theme.Light -> false
                Theme.Dark -> true
            }
        }
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
