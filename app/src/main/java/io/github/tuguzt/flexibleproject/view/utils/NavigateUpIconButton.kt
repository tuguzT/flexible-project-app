package io.github.tuguzt.flexibleproject.view.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.theme.AppTheme

@Composable
fun NavigateUpIconButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    contentDescription: String? = stringResource(R.string.navigate_up),
) {
    IconButton(onClick = onClick, enabled = enabled) {
        Icon(
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = contentDescription,
        )
    }
}

@Preview
@Composable
private fun NavigateUpIconButton() {
    AppTheme {
        NavigateUpIconButton(onClick = {})
    }
}
