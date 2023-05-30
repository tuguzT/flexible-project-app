package io.github.tuguzt.flexibleproject.view.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.rememberNavHostEngine
import io.github.tuguzt.flexibleproject.view.screens.NavGraphs
import io.github.tuguzt.flexibleproject.view.screens.destinations.BasicScreenDestination
import io.github.tuguzt.flexibleproject.viewmodel.auth.SignInViewModel
import io.github.tuguzt.flexibleproject.viewmodel.auth.SignUpViewModel
import io.github.tuguzt.flexibleproject.viewmodel.user.CurrentUserViewModel

@RootNavGraph
@Destination
@Composable
fun AuthScreen(
    navigator: DestinationsNavigator,
    viewModel: CurrentUserViewModel,
    signInViewModel: SignInViewModel = hiltViewModel(),
    signUpViewModel: SignUpViewModel = hiltViewModel(),
) {
    val engine = rememberNavHostEngine()
    val navController = engine.rememberNavController()

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(state) {
        if (state.currentUser != null) {
            val direction = BasicScreenDestination()
            navigator.navigate(direction) {
                popUpTo(NavGraphs.root) {
                    inclusive = true
                }
            }
        }
    }

    DestinationsNavHost(
        engine = engine,
        navController = navController,
        navGraph = NavGraphs.auth,
        dependenciesContainerBuilder = {
            dependency(signInViewModel)
            dependency(signUpViewModel)
        },
    )
}
