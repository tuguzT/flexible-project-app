package io.github.tuguzt.flexibleproject.view.screen.main.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.navigation.MainScreenDestination.Home
import io.github.tuguzt.flexibleproject.viewmodel.main.MainViewModel

@Composable
fun HomeScreen(
    destination: Home = Home(LocalContext.current),
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val appName = context.getString(R.string.app_name)

    LaunchedEffect(Unit) {
        mainViewModel.updateTitle(appName)
    }
    Text(text = destination.title)
}
