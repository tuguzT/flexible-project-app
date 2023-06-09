package io.github.tuguzt.flexibleproject.view.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.theme.AppTheme

@Composable
fun ConfirmDialog(
    title: String,
    supportText: String,
    onConfirmation: (Boolean) -> Unit,
) {
    Surface(shape = MaterialTheme.shapes.large) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ConfirmDialogTitle(title = title)
                ConfirmDialogSupportText(supportText = supportText)
            }
            ConfirmDialogButtons(
                title = title,
                onCancel = { onConfirmation(false) },
                onConfirm = { onConfirmation(true) },
                modifier = Modifier.align(Alignment.End),
            )
        }
    }
}

@Composable
private fun ConfirmDialogTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    OneLineTitle(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier,
    )
}

@Composable
private fun ConfirmDialogSupportText(
    supportText: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = supportText,
        modifier = modifier,
    )
}

@Composable
private fun ConfirmDialogButtons(
    title: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TextButton(onClick = onCancel, text = stringResource(R.string.cancel))
        TextButton(onClick = onConfirm, text = title)
    }
}

@Preview
@Composable
private fun ConfirmDialog() {
    AppTheme {
        ConfirmDialog(
            title = "Lorem Ipsum",
            supportText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Morbi dictum sodales ex, sit amet maximus nisi aliquet ut. Interdum et.",
            onConfirmation = {},
        )
    }
}
