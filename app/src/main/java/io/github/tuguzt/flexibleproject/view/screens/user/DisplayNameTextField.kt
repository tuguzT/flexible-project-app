package io.github.tuguzt.flexibleproject.view.screens.user

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.utils.SingleLineTextField

@Composable
fun DisplayNameTextField(
    displayName: String,
    onDisplayNameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = stringResource(R.string.display_name),
) {
    SingleLineTextField(
        value = displayName,
        onValueChange = onDisplayNameChange,
        modifier = modifier,
        enabled = enabled,
        label = label,
    )
}
