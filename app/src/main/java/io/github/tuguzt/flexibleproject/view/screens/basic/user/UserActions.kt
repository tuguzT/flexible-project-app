package io.github.tuguzt.flexibleproject.view.screens.basic.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle

@Suppress("UnusedReceiverParameter")
@Composable
fun RowScope.UserActions(
    onShareClick: () -> Unit,
    menuExpanded: Boolean,
    onMenuExpandedChange: (Boolean) -> Unit,
    onEditClick: (() -> Unit)?,
    onSignOutClick: (() -> Unit)?,
    onDeleteClick: (() -> Unit)?,
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
        onSignOutClick = onSignOutClick,
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
            contentDescription = stringResource(R.string.share_user),
        )
    }
}

@Composable
private fun MenuActions(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onEditClick: (() -> Unit)?,
    onSignOutClick: (() -> Unit)?,
    onDeleteClick: (() -> Unit)?,
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
        onEditClick?.let {
            EditUserDropdownMenuItem(onClick = it)
        }
        onSignOutClick?.let {
            SignOutDropdownMenuItem(onClick = it)
        }
        onDeleteClick?.let {
            DeleteUserDropdownMenuItem(onClick = it)
        }
    }
}

@Composable
private fun EditUserDropdownMenuItem(
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
private fun DeleteUserDropdownMenuItem(
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

@Composable
private fun SignOutDropdownMenuItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenuItem(
        text = { OneLineTitle(text = stringResource(R.string.sign_out)) },
        onClick = onClick,
        modifier = modifier,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Logout,
                contentDescription = null,
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun UserActions() {
    val topBar: @Composable () -> Unit = {
        var expanded by remember { mutableStateOf(false) }
        TopAppBar(
            title = { OneLineTitle(text = "Preview") },
            actions = {
                UserActions(
                    onShareClick = {},
                    menuExpanded = expanded,
                    onMenuExpandedChange = { expanded = it },
                    onEditClick = {},
                    onSignOutClick = {},
                    onDeleteClick = {},
                )
            },
        )
    }

    AppTheme {
        Scaffold(topBar = topBar) { padding ->
            Box(modifier = Modifier.padding(padding))
        }
    }
}
