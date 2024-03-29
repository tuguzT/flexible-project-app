package io.github.tuguzt.flexibleproject.view.screens.basic.user

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.utils.SingleLineTextField

@Composable
fun EmailTextField(
    email: String,
    onEmailChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = stringResource(R.string.email),
) {
    SingleLineTextField(
        value = email,
        onValueChange = onEmailChange,
        modifier = modifier,
        enabled = enabled,
        label = label,
    )
}
