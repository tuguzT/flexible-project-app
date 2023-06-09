package io.github.tuguzt.flexibleproject.view.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.theme.AppTheme

@Composable
fun NameTextField(
    name: String,
    onNameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.name),
    enabled: Boolean = true,
) {
    SingleLineTextField(
        value = name,
        onValueChange = onNameChange,
        label = label,
        modifier = modifier,
        enabled = enabled,
    )
}

@Preview
@Composable
private fun NameTextField() {
    AppTheme {
        Surface {
            NameTextField(
                name = "Hello World",
                onNameChange = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
