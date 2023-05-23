package io.github.tuguzt.flexibleproject.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import io.github.tuguzt.flexibleproject.viewmodel.user.CurrentUserViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: CurrentUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            val state = viewModel.stateFlow.value
            state.loading
        }

        super.onCreate(savedInstanceState)
        setContent {
            MainActivityContent(viewModel)
        }
    }
}
