package io.github.tuguzt.flexibleproject.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import io.github.tuguzt.flexibleproject.domain.model.user.UserCredentials
import io.github.tuguzt.flexibleproject.viewmodel.auth.AuthViewModel
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStore

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            val state = authViewModel.stateFlow.value
            state.isLoading
        }

        super.onCreate(savedInstanceState)
        setContent {
            MainActivityContent(authViewModel)
        }

        // TODO remove when auth flow will be implemented
        val credentials = UserCredentials(name = "tuguzT", password = "")
        val intent = AuthStore.Intent.SignIn(credentials)
        authViewModel.accept(intent)
    }
}
