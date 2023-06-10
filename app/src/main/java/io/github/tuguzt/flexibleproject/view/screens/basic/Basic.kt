package io.github.tuguzt.flexibleproject.view.screens.basic

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.dependency
import io.github.tuguzt.flexibleproject.view.screens.NavGraphs
import io.github.tuguzt.flexibleproject.viewmodel.basic.settings.SettingsViewModel
import io.github.tuguzt.flexibleproject.viewmodel.basic.user.CurrentUserViewModel

@OptIn(
    ExperimentalMaterialNavigationApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterial3Api::class,
)
@RootNavGraph(start = true)
@Destination
@Composable
fun BasicScreen(
    currentUserViewModel: CurrentUserViewModel,
    settingsViewModel: SettingsViewModel,
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

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = BottomSheetDefaults.ExpandedShape,
        sheetElevation = BottomSheetDefaults.Elevation,
        sheetBackgroundColor = BottomSheetDefaults.ContainerColor,
        sheetContentColor = MaterialTheme.colorScheme.onSurface,
        scrimColor = BottomSheetDefaults.ScrimColor,
    ) {
        DestinationsNavHost(
            navGraph = NavGraphs.basic,
            engine = engine,
            navController = navController,
            dependenciesContainerBuilder = {
                dependency(currentUserViewModel)
                dependency(settingsViewModel)
            },
        )
    }
}
