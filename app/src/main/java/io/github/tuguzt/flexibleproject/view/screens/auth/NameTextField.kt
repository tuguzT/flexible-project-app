package io.github.tuguzt.flexibleproject.view.screens.auth

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle

@Composable
fun NameTextField(
    name: String,
    onNameChange: (String) -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.name),
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        modifier = modifier,
        enabled = enabled,
        label = { OneLineTitle(text = label) },
        singleLine = true,
    )
}
