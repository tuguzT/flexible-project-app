package io.github.tuguzt.flexibleproject.view.screens.about

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph
@Destination
@Composable
fun AboutScreen(
    navigator: DestinationsNavigator,
) {
    Scaffold(
        topBar = {
            AboutTopBar(onNavigationClick = navigator::navigateUp)
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding))
    }
}
