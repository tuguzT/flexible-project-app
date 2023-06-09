package io.github.tuguzt.flexibleproject.view.screens.workspace

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle

@Composable
fun DeleteWorkspaceContent(
    onConfirmation: (Boolean) -> Unit,
) {
    Surface(shape = MaterialTheme.shapes.large) {
        Column(modifier = Modifier.padding(8.dp)) {
            Column(modifier = Modifier.padding(12.dp)) {
                DeleteWorkspaceTitle()
                Spacer(modifier = Modifier.height(8.dp))
                DeleteWorkspaceSupportingText()
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.align(Alignment.End)) {
                CancelButton(onClick = { onConfirmation(false) })
                Spacer(modifier = Modifier.width(8.dp))
                DeleteButton(onClick = { onConfirmation(true) })
            }
        }
    }
}

@Composable
private fun DeleteWorkspaceTitle(
    modifier: Modifier = Modifier,
) {
    OneLineTitle(
        text = stringResource(R.string.delete),
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier,
    )
}

@Composable
private fun DeleteWorkspaceSupportingText(
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(R.string.delete_workspace_confirmation),
        modifier = modifier,
    )
}

@Composable
private fun CancelButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        OneLineTitle(text = stringResource(R.string.cancel))
    }
}

@Composable
private fun DeleteButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        OneLineTitle(text = stringResource(R.string.delete))
    }
}

@Preview
@Composable
private fun DeleteWorkspaceContent() {
    AppTheme {
        DeleteWorkspaceContent(
            onConfirmation = {},
        )
    }
}
