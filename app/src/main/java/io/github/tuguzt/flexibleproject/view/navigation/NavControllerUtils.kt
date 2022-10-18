package io.github.tuguzt.flexibleproject.view.navigation

import androidx.navigation.NavController
import io.github.tuguzt.flexibleproject.view.navigation.RootNavigationDestinations.Auth
import io.github.tuguzt.flexibleproject.view.navigation.RootNavigationDestinations.Main

fun NavController.navigateAuth(popUpToDestination: Destination = Main) = navigate(Auth.route) {
    popUpTo(popUpToDestination.route) {
        inclusive = true
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}

fun NavController.navigateMain(popUpToDestination: Destination = Auth) = navigate(Main.route) {
    popUpTo(popUpToDestination.route) {
        inclusive = true
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}
