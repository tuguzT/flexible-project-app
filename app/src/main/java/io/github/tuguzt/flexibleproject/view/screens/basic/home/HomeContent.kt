package io.github.tuguzt.flexibleproject.view.screens.basic.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.theme.AppTheme

@Composable
fun HomeContent(
    onAddClick: () -> Unit,
    sheetExpanded: Boolean,
    onSheetExpandedChange: (Boolean) -> Unit,
    onAddWorkspaceClick: () -> Unit,
    onAddProjectClick: () -> Unit,
    onAddMethodologyClick: () -> Unit,
) {
    Scaffold(
        floatingActionButton = { HomeAddFAB(onClick = onAddClick) },
    ) { padding ->
        // TODO provide some content
        Box(modifier = Modifier.padding(padding))

        HomeBottomSheet(
            expanded = sheetExpanded,
            onExpandedChange = onSheetExpandedChange,
            onAddWorkspaceClick = onAddWorkspaceClick,
            onAddProjectClick = onAddProjectClick,
            onAddMethodologyClick = onAddMethodologyClick,
        )
    }
}

@Composable
private fun HomeAddFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = stringResource(R.string.add),
        )
    }
}

@Preview
@Composable
private fun HomeContent() {
    AppTheme {
        HomeContent(
            onAddClick = {},
            sheetExpanded = true,
            onSheetExpandedChange = {},
            onAddWorkspaceClick = {},
            onAddProjectClick = {},
            onAddMethodologyClick = {},
        )
    }
}
