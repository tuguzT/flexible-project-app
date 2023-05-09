package io.github.tuguzt.flexibleproject.view.screens.auth

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.rememberNavHostEngine
import io.github.tuguzt.flexibleproject.view.screens.NavGraphs

@RootNavGraph
@Destination
@Composable
fun AuthScreen(
    navigator: DestinationsNavigator,
) {
    val engine = rememberNavHostEngine()
    val navController = engine.rememberNavController()

    DestinationsNavHost(
        engine = engine,
        navController = navController,
        navGraph = NavGraphs.auth,
    )
}
