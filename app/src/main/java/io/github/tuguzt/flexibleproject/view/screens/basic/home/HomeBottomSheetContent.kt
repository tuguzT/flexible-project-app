package io.github.tuguzt.flexibleproject.view.screens.basic.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Article
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.Groups3
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle

@Composable
fun HomeBottomSheetContent(
    onAddWorkspaceClick: () -> Unit,
    onAddProjectClick: () -> Unit,
    onAddMethodologyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        AddWorkspaceButton(onClick = onAddWorkspaceClick)
        AddProjectButton(onClick = onAddProjectClick)
        AddMethodologyButton(onClick = onAddMethodologyClick)
    }
}

@Composable
private fun AddWorkspaceButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicBottomSheetButton(
        text = stringResource(R.string.workspace),
        icon = Icons.Rounded.Groups3,
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
private fun AddProjectButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicBottomSheetButton(
        text = stringResource(R.string.project),
        icon = Icons.Rounded.Dashboard,
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
private fun AddMethodologyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicBottomSheetButton(
        text = stringResource(R.string.methodology),
        icon = Icons.Rounded.Article,
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
private fun BasicBottomSheetButton(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    iconDescription: String? = null,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        modifier = modifier.size(96.dp),
        shape = CircleShape,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 4.dp,
                alignment = Alignment.CenterVertically,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = iconDescription,
                modifier = Modifier.size(24.dp),
            )
            OneLineTitle(
                text = text,
                overflow = TextOverflow.Visible,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Preview
@Composable
private fun HomeBottomSheetContent() {
    AppTheme {
        Surface(tonalElevation = 1.dp) {
            HomeBottomSheetContent(
                onAddWorkspaceClick = {},
                onAddProjectClick = {},
                onAddMethodologyClick = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
