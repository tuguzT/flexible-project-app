package io.github.tuguzt.flexibleproject.view.screens.about

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.tuguzt.flexibleproject.view.screens.basic.BasicNavGraph

@BasicNavGraph
@Destination
@Composable
fun AboutScreen(
    navigator: DestinationsNavigator,
) {
    AboutScreenContent(onNavigationClick = navigator::navigateUp)
}
