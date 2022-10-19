package io.github.tuguzt.flexibleproject.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import io.github.tuguzt.flexibleproject.view.navigation.RootNavigationDestinations.Main
import io.github.tuguzt.flexibleproject.view.screen.RootScreen
import io.github.tuguzt.flexibleproject.view.theme.FlexibleProjectTheme
import io.github.tuguzt.flexibleproject.viewmodel.main.account.AccountViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val accountViewModel: AccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
//        installSplashScreen().setKeepOnScreenCondition { todo
//            accountViewModel.uiState.isLoading
//        }
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContent {
            FlexibleProjectTheme {
//                val startDestination = if (accountViewModel.uiState.isSignedIn) Main else Auth todo
                RootScreen(
//                    startDestination = startDestination,
                    startDestination = Main,
                    accountViewModel = accountViewModel,
                )
            }
        }
    }
}
