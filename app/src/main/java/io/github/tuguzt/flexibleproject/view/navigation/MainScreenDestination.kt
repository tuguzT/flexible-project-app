package io.github.tuguzt.flexibleproject.view.navigation

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.tuguzt.flexibleproject.R

sealed class MainScreenDestination(
    override val route: String,
    override val icon: ImageVector,
    override val title: String,
) : ImageDestination, TitleDestination {

    class Home(context: Context) : MainScreenDestination(
        route = "home",
        icon = Icons.Rounded.Home,
        title = context.getString(R.string.home),
    )

    class Settings(context: Context) : MainScreenDestination(
        route = "settings",
        icon = Icons.Rounded.Settings,
        title = context.getString(R.string.settings),
    )
}
