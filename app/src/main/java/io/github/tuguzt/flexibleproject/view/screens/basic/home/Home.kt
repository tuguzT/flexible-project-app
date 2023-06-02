package io.github.tuguzt.flexibleproject.view.screens.basic.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.tuguzt.flexibleproject.view.screens.destinations.AddWorkspaceScreenDestination

@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
) {
    var sheetExpanded by remember { mutableStateOf(false) }

    HomeContent(
        onAddClick = { sheetExpanded = true },
        sheetExpanded = sheetExpanded,
        onSheetExpandedChange = { sheetExpanded = it },
        onAddWorkspaceClick = {
            sheetExpanded = false
            val direction = AddWorkspaceScreenDestination()
            navigator.navigate(direction)
        },
        onAddProjectClick = {
            sheetExpanded = false
            // TODO
        },
        onAddMethodologyClick = {
            sheetExpanded = false
            // TODO
        },
    )
}
