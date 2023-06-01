package io.github.tuguzt.flexibleproject.view.screens.auth

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.dependency
import io.github.tuguzt.flexibleproject.view.screens.NavGraphs
import io.github.tuguzt.flexibleproject.viewmodel.auth.SignInViewModel
import io.github.tuguzt.flexibleproject.viewmodel.auth.SignUpViewModel

@RootNavGraph
@Destination
@Composable
fun AuthScreen(
    signInViewModel: SignInViewModel = hiltViewModel(),
    signUpViewModel: SignUpViewModel = hiltViewModel(),
) {
    DestinationsNavHost(
        navGraph = NavGraphs.auth,
        dependenciesContainerBuilder = {
            dependency(signInViewModel)
            dependency(signUpViewModel)
        },
    )
}
