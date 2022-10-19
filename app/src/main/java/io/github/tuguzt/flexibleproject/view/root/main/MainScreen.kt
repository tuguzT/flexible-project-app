package io.github.tuguzt.flexibleproject.view.root.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.tuguzt.flexibleproject.view.navigation.MainScreenDestination.*
import io.github.tuguzt.flexibleproject.viewmodel.main.MainViewModel
import io.github.tuguzt.flexibleproject.viewmodel.main.account.AccountViewModel
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.root.main.home.HomeScreen
import io.github.tuguzt.flexibleproject.view.root.main.settings.SettingsScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    accountViewModel: AccountViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    val context = LocalContext.current
    val appName = context.getString(R.string.app_name)
    val home = remember(context) { Home(context) }
    val settings = remember(context) { Settings(context) }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        mainViewModel.run {
            updateTitle(appName)
            updateOnNavigationIconAction {
                coroutineScope.launch { drawerState.open() }
            }
        }
    }

    MainScreenNavigationDrawer(
        drawerState = drawerState,
        navController = navController,
        coroutineScope = coroutineScope,
        destinations = listOf(home, settings),
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { MainScreenTopAppBar(mainViewModel = mainViewModel) },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        ) { padding ->
            NavHost(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                navController = navController,
                startDestination = home.route,
            ) {
                composable(route = home.route) {
                    HomeScreen(home, mainViewModel)
                }
                composable(route = settings.route) {
                    SettingsScreen(settings, mainViewModel)
                }
            }
        }
    }
}
