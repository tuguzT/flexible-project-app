package io.github.tuguzt.flexibleproject.view.screens.user

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle

@Composable
fun UpdateDisplayNameTextField(
    displayName: String,
    onDisplayNameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = stringResource(R.string.update_display_name),
) {
    OutlinedTextField(
        value = displayName,
        onValueChange = onDisplayNameChange,
        modifier = modifier,
        enabled = enabled,
        label = { OneLineTitle(text = label) },
        singleLine = true,
    )
}
