package io.github.tuguzt.flexibleproject.view.screens.basic.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import io.github.tuguzt.flexibleproject.view.screens.basic.BasicNavGraph
import io.github.tuguzt.flexibleproject.view.screens.destinations.AddWorkspaceScreenDestination

@BasicNavGraph
@Destination(style = DestinationStyle.BottomSheet::class)
@Composable
fun HomeBottomSheet(
    navigator: DestinationsNavigator,
) {
    HomeBottomSheetContent(
        onAddWorkspaceClick = {
            navigator.navigateUp()
            val direction = AddWorkspaceScreenDestination()
            navigator.navigate(direction)
        },
        onAddProjectClick = {
            navigator.navigateUp()
            // TODO
        },
        onAddMethodologyClick = {
            navigator.navigateUp()
            // TODO
        },
        modifier = Modifier.fillMaxWidth(),
    )
}
