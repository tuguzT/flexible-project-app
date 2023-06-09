package io.github.tuguzt.flexibleproject.view.screens.basic

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicBottomSheet(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onAddWorkspaceClick: () -> Unit,
    onAddProjectClick: () -> Unit,
    onAddMethodologyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var trulyExpanded by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(expanded) {
        if (expanded) {
            trulyExpanded = true
            sheetState.expand()
        } else {
            sheetState.hide()
            trulyExpanded = false
        }
    }

    if (!trulyExpanded) return
    ModalBottomSheet(
        onDismissRequest = { onExpandedChange(false) },
        modifier = modifier,
        sheetState = sheetState,
        dragHandle = null,
    ) {
        BasicBottomSheetContent(
            onAddWorkspaceClick = onAddWorkspaceClick,
            onAddProjectClick = onAddProjectClick,
            onAddMethodologyClick = onAddMethodologyClick,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun BasicBottomSheetContent(
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
private fun BasicBottomSheet() {
    AppTheme {
        BasicBottomSheet(
            expanded = true,
            onExpandedChange = {},
            onAddWorkspaceClick = {},
            onAddProjectClick = {},
            onAddMethodologyClick = {},
        )
    }
}

@Preview
@Composable
private fun BasicBottomSheetContent() {
    AppTheme {
        Surface(tonalElevation = 1.dp) {
            BasicBottomSheetContent(
                onAddWorkspaceClick = {},
                onAddProjectClick = {},
                onAddMethodologyClick = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
