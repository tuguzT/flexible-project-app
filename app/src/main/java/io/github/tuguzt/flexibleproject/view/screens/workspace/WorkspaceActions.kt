package io.github.tuguzt.flexibleproject.view.screens.workspace

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle

@Suppress("UnusedReceiverParameter")
@Composable
fun RowScope.WorkspaceActions(
    onShareClick: () -> Unit,
    menuExpanded: Boolean,
    onMenuExpandedChange: (Boolean) -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    enabled: Boolean = true,
) {
    ShareAction(
        onClick = onShareClick,
        enabled = enabled,
    )
    MenuActions(
        expanded = menuExpanded,
        onExpandedChange = onMenuExpandedChange,
        onEditClick = onEditClick,
        onDeleteClick = onDeleteClick,
        enabled = enabled,
    )
}

@Composable
private fun ShareAction(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        Icon(
            imageVector = Icons.Rounded.Share,
            contentDescription = stringResource(R.string.share_workspace),
        )
    }
}

@Composable
private fun MenuActions(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    IconButton(
        onClick = { onExpandedChange(!expanded) },
        modifier = modifier,
        enabled = enabled,
    ) {
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = stringResource(R.string.more_actions),
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onExpandedChange(false) },
    ) {
        EditWorkspaceDropdownMenuItem(onClick = onEditClick)
        DeleteWorkspaceDropdownMenuItem(onClick = onDeleteClick)
    }
}

@Composable
fun EditWorkspaceDropdownMenuItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenuItem(
        text = { OneLineTitle(text = stringResource(R.string.edit)) },
        onClick = onClick,
        modifier = modifier,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = null,
            )
        },
    )
}

@Composable
fun DeleteWorkspaceDropdownMenuItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenuItem(
        text = { OneLineTitle(text = stringResource(R.string.delete)) },
        onClick = onClick,
        modifier = modifier,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = null,
            )
        },
    )
}
