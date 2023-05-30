package io.github.tuguzt.flexibleproject.view.screens.auth

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibleChange: (Boolean) -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.password),
    trailingIconVisible: Boolean = true,
) {
    val visualTransformation = if (passwordVisible) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = modifier,
        enabled = enabled,
        label = { OneLineTitle(text = label) },
        singleLine = true,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = block@{
            if (!trailingIconVisible) return@block

            val imageVector = if (passwordVisible) {
                Icons.Rounded.Visibility
            } else {
                Icons.Rounded.VisibilityOff
            }
            val contentDescription = if (passwordVisible) {
                stringResource(R.string.hide_password)
            } else {
                stringResource(R.string.show_password)
            }
            IconButton(onClick = { onPasswordVisibleChange(!passwordVisible) }) {
                Icon(imageVector, contentDescription)
            }
        },
    )
}
