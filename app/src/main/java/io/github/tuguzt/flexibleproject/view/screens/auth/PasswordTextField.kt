package io.github.tuguzt.flexibleproject.view.screens.auth

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.SingleLineTextField

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibleChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = stringResource(R.string.password),
    trailingIconVisible: Boolean = true,
) {
    val visualTransformation = if (passwordVisible) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }

    SingleLineTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = label,
        modifier = modifier,
        enabled = enabled,
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

@Preview
@Composable
private fun PasswordTextField() {
    AppTheme {
        Surface {
            PasswordTextField(
                password = "Some password value",
                onPasswordChange = {},
                passwordVisible = false,
                onPasswordVisibleChange = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
