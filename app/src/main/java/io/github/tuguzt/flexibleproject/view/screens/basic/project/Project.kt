package io.github.tuguzt.flexibleproject.view.screens.basic.project

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.tuguzt.flexibleproject.view.screens.basic.BasicNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@BasicNavGraph
@Destination
@Composable
fun ProjectScreen(
    id: String,
    navigator: DestinationsNavigator,
) {
    ProjectContent(
        project = null, // TODO view model
        loading = false, // TODO view model
        onNavigationClick = navigator::navigateUp,
        topBarActions = { /* TODO */ },
    )
}
