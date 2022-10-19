package io.github.tuguzt.flexibleproject.view.root.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.tuguzt.flexibleproject.viewmodel.main.MainViewModel
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.theme.FlexibleProjectTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreenTopAppBar(mainViewModel: MainViewModel = hiltViewModel()) {
    val tonalElevation by animateDpAsState(if (mainViewModel.uiState.isTopBarFilled) 4.dp else 0.dp)

    Surface(tonalElevation = tonalElevation) {
        CenterAlignedTopAppBar(
            title = {
                AnimatedContent(targetState = mainViewModel.uiState.title) { title -> Text(title) }
            },
            navigationIcon = {
                IconButton(onClick = mainViewModel.uiState.onNavigationIconAction) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = stringResource(R.string.menu),
                    )
                }
            },
        )
    }
}

@Preview
@Composable
private fun MainScreenTopAppBarPreview() {
    val mainViewModel = MainViewModel().apply {
        updateTitle(title = stringResource(R.string.app_name))
    }
    FlexibleProjectTheme {
        MainScreenTopAppBar(mainViewModel)
    }
}
