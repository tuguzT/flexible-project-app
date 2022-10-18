package io.github.tuguzt.flexibleproject.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import io.github.tuguzt.flexibleproject.view.navigation.RootNavigationDestinations
import io.github.tuguzt.flexibleproject.view.root.RootScreen
import io.github.tuguzt.flexibleproject.view.theme.FlexibleProjectTheme
import io.github.tuguzt.flexibleproject.viewmodel.main.account.AccountViewModel

class MainActivity : ComponentActivity() {
    private val accountViewModel: AccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
//        installSplashScreen().setKeepOnScreenCondition {
//            accountViewModel.uiState.isLoading
//        }
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContent {
            FlexibleProjectTheme {
//                val startDestination = when (accountViewModel.uiState.signedIn) {
//                    true -> RootNavigationDestinations.Main
//                    false -> RootNavigationDestinations.Auth
//                }
                val startDestination = RootNavigationDestinations.Main
                RootScreen(
                    startDestination = startDestination,
                    accountViewModel = accountViewModel,
                )
            }
        }
    }
}
