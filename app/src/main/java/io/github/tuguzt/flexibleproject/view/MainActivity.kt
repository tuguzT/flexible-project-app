package io.github.tuguzt.flexibleproject.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import io.github.tuguzt.flexibleproject.viewmodel.basic.settings.SettingsViewModel
import io.github.tuguzt.flexibleproject.viewmodel.basic.user.CurrentUserViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val currentUserViewModel: CurrentUserViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            val currentUserState = currentUserViewModel.stateFlow.value
            val settingsState = settingsViewModel.stateFlow.value
            currentUserState.loading && settingsState.loading
        }

        super.onCreate(savedInstanceState)
        setContent {
            MainActivityContent(currentUserViewModel, settingsViewModel)
        }
    }
}
