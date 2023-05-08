package io.github.tuguzt.flexibleproject.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import io.github.tuguzt.flexibleproject.view.screens.NavGraphs
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.viewmodel.user.UserViewModel

@Composable
fun MainActivityContent(
    userViewModel: UserViewModel = hiltViewModel(),
) {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            DestinationsNavHost(
                navGraph = NavGraphs.root,
                dependenciesContainerBuilder = {
                    dependency(userViewModel)
                },
            )
        }
    }
}
