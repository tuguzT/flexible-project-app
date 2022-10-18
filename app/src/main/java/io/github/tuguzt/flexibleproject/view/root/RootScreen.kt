package io.github.tuguzt.flexibleproject.view.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.tuguzt.flexibleproject.view.navigation.RootNavigationDestinations
import io.github.tuguzt.flexibleproject.view.navigation.RootNavigationDestinations.Main
import io.github.tuguzt.flexibleproject.view.navigation.navigateAuth
import io.github.tuguzt.flexibleproject.view.root.auth.authGraph
import io.github.tuguzt.flexibleproject.view.root.main.MainScreen
import io.github.tuguzt.flexibleproject.viewmodel.auth.AuthViewModel
import io.github.tuguzt.flexibleproject.viewmodel.main.account.AccountViewModel
import io.github.tuguzt.flexibleproject.viewmodel.main.account.isSignedIn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen(
    startDestination: RootNavigationDestinations,
    authViewModel: AuthViewModel = hiltViewModel(),
    accountViewModel: AccountViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    Scaffold { padding ->
        NavHost(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            navController = navController,
            startDestination = startDestination.route,
        ) {
            composable(route = Main.route) {
                LaunchedEffect(accountViewModel.uiState) {
                    if (accountViewModel.uiState.isLoading || authViewModel.uiState.isLoading)
                        return@LaunchedEffect

                    if (!accountViewModel.uiState.isSignedIn) {
                        authViewModel.updateIsLoggedIn(isLoggedIn = false)
                        navController.navigateAuth()
                    }
                }
                MainScreen()
            }
            authGraph(navController, authViewModel, accountViewModel)
        }
    }
}
