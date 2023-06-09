package io.github.tuguzt.flexibleproject.view.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
        Column(modifier = Modifier.padding(8.dp)) {
            Column(modifier = Modifier.padding(12.dp)) {
                ConfirmDialogTitle(title = title)
                Spacer(modifier = Modifier.height(8.dp))
                ConfirmDialogSupportText(supportText = supportText)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.align(Alignment.End)) {
                TextButton(
                    text = stringResource(R.string.cancel),
                    onClick = { onConfirmation(false) },
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(
                    text = title,
                    onClick = { onConfirmation(true) },
                )
            }
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
