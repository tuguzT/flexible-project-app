package io.github.tuguzt.flexibleproject.view.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.theme.AppTheme

@Composable
fun CloseIconButton(
    onClick: () -> Unit,
    contentDescription: String? = stringResource(R.string.close),
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Rounded.Close,
            contentDescription = contentDescription,
        )
    }
}

@Preview
@Composable
private fun CloseIconButton() {
    AppTheme {
        CloseIconButton(onClick = {})
    }
}
