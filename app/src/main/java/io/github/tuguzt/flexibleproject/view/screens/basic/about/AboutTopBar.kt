package io.github.tuguzt.flexibleproject.view.screens.basic.about

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.NavigateUpIconButton
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutTopBar(
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.about),
) {
    TopAppBar(
        modifier = modifier,
        title = { OneLineTitle(text = title) },
        navigationIcon = { NavigateUpIconButton(onClick = onNavigationClick) },
    )
}

@Preview
@Composable
private fun AboutTopBar() {
    AppTheme {
        Scaffold(
            topBar = {
                AboutTopBar(onNavigationClick = {})
            },
        ) { padding ->
            Box(modifier = Modifier.padding(padding))
        }
    }
}
