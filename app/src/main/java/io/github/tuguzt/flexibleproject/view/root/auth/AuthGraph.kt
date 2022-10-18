package io.github.tuguzt.flexibleproject.view.root.auth

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.navigation.RootNavigationDestinations.Auth
import io.github.tuguzt.flexibleproject.view.navigation.navigateMain
import io.github.tuguzt.flexibleproject.viewmodel.BackendErrorKind
import io.github.tuguzt.flexibleproject.viewmodel.auth.AuthMessageKind
import io.github.tuguzt.flexibleproject.viewmodel.auth.AuthViewModel
import io.github.tuguzt.flexibleproject.viewmodel.main.account.AccountViewModel

fun NavGraphBuilder.authGraph(
    navController: NavController,
    authViewModel: AuthViewModel,
    accountViewModel: AccountViewModel,
) = navigation(startDestination = Auth.SignIn.route, route = Auth.route) {
    composable(Auth.SignIn.route) {
        val snackbarHostState = remember { SnackbarHostState() }
//        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        val onSignIn: (AuthVariant) -> Unit = { variant ->
            when (variant) {
                AuthVariant.Credentials -> authViewModel.auth()
//                AuthVariant.Google -> {
//                    val intent = authViewModel.googleSignInIntent
//                    launcher.launch(intent)
//                }
            }
        }

        LaunchedEffect(authViewModel.uiState) {
            if (authViewModel.uiState.isLoading) return@LaunchedEffect

            if (authViewModel.uiState.isLoggedIn) {
                accountViewModel.updateUser()
                navController.navigateMain()
            }
        }

        SignInScreen(
            onSignIn = onSignIn,
            viewModel = authViewModel,
            onSignUpNavigate = {
                navController.navigate(Auth.SignUp.route) {
                    popUpTo(Auth.route) { inclusive = true }
                }
            },
            snackbarHostState = snackbarHostState,
        )

        authViewModel.uiState.messages.firstOrNull()?.let { message ->
            LaunchedEffect(message) {
                snackbarHostState.showSnackbar(
                    message = message.kind.message(context),
                    actionLabel = context.getString(R.string.dismiss),
                )
                authViewModel.userMessageShown(message.id)
            }
        }
    }
    composable(Auth.SignUp.route) {
        val snackbarHostState = remember { SnackbarHostState() }
//        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        val onSignUp: (AuthVariant) -> Unit = { variant ->
            when (variant) {
                AuthVariant.Credentials -> authViewModel.register()
//                AuthVariant.Google -> {
//                    val intent = authViewModel.googleSignInIntent
//                    launcher.launch(intent)
//                }
            }
        }

        LaunchedEffect(authViewModel.uiState) {
            if (authViewModel.uiState.isLoading) return@LaunchedEffect

            if (authViewModel.uiState.isLoggedIn) {
                accountViewModel.updateUser()
                navController.navigateMain()
            }
        }

        SignUpScreen(
            onSignUp = onSignUp,
            viewModel = authViewModel,
            onSignInNavigate = {
                navController.navigate(Auth.SignIn.route) {
                    popUpTo(Auth.route) { inclusive = true }
                }
            },
        )

        authViewModel.uiState.messages.firstOrNull()?.let { message ->
            LaunchedEffect(message) {
                snackbarHostState.showSnackbar(
                    message = message.kind.message(context),
                    actionLabel = context.getString(R.string.dismiss),
                )
                authViewModel.userMessageShown(message.id)
            }
        }
    }
}

private fun AuthMessageKind.message(context: Context): String = when (this) {
    is AuthMessageKind.Backend -> when (this.backendErrorKind) {
        BackendErrorKind.ServerError -> context.getString(R.string.server_error)
        BackendErrorKind.NetworkError -> context.getString(R.string.network_error)
        BackendErrorKind.UnknownError -> context.getString(R.string.unknown_error)
    }
}
