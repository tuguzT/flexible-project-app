package io.github.tuguzt.flexibleproject.view.screen.main.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.tuguzt.flexibleproject.view.navigation.MainScreenDestination.Settings
import io.github.tuguzt.flexibleproject.viewmodel.main.MainViewModel

@Composable
fun SettingsScreen(
    destination: Settings = Settings(LocalContext.current),
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        mainViewModel.updateTitle(destination.title)
    }
    Text(text = destination.title)
}
