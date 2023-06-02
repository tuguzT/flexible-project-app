package io.github.tuguzt.flexibleproject.view.screens.workspace

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph
@Destination
@Composable
fun EditWorkspaceScreen(
    navigator: DestinationsNavigator,
) {
    EditWorkspaceContent(
        onNavigationClick = navigator::navigateUp,
    )
}
