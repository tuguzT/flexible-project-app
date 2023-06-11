package io.github.tuguzt.flexibleproject.view.screens.basic.about

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.tuguzt.flexibleproject.view.screens.basic.BasicNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@BasicNavGraph
@Destination
@Composable
fun AboutScreen(
    navigator: DestinationsNavigator,
) {
    AboutScreenContent(onNavigationClick = navigator::navigateUp)
}
