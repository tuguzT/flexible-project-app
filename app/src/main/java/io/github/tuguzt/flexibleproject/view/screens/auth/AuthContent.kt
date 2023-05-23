package io.github.tuguzt.flexibleproject.view.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle

@Composable
fun AuthContent(
    title: String,
    name: String,
    onNameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibleChange: (Boolean) -> Unit,
    submitText: String,
    onSubmit: () -> Unit,
    submitEnabled: Boolean,
    loading: Boolean,
    changeAuthTypeText: String,
    changeAuthTypeClickableText: String,
    onChangeAuthType: () -> Unit,
    focusManager: FocusManager = LocalFocusManager.current,
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = focusManager::clearFocus,
                )
                .padding(48.dp),
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            AppNameWithLogo(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(48.dp))
            AuthTitle(
                title = title,
                modifier = Modifier.fillMaxWidth(),
            )

            AuthFields(
                modifier = Modifier.weight(1f).padding(vertical = 32.dp),
                name = name,
                onNameChange = onNameChange,
                password = password,
                onPasswordChange = onPasswordChange,
                passwordVisible = passwordVisible,
                onPasswordVisibleChange = onPasswordVisibleChange,
                submitText = submitText,
                onSubmit = onSubmit,
                submitEnabled = submitEnabled,
                loading = loading,
            )

            ChangeAuthTypeText(
                changeAuthTypeText = changeAuthTypeText,
                changeAuthTypeClickableText = changeAuthTypeClickableText,
                modifier = Modifier.fillMaxWidth(),
                onChangeAuthType = onChangeAuthType,
                loading = loading,
            )
        }
    }
}

@Composable
private fun AppNameWithLogo(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.size(128.dp),
        )
        Spacer(modifier = Modifier.height(8.dp))
        OneLineTitle(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}

@Composable
private fun AuthTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        OneLineTitle(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@Composable
private fun AuthFields(
    name: String,
    onNameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibleChange: (Boolean) -> Unit,
    submitText: String,
    onSubmit: () -> Unit,
    submitEnabled: Boolean,
    loading: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NameTextField(
            name = name,
            onNameChange = onNameChange,
            enabled = !loading,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField(
            password = password,
            onPasswordChange = onPasswordChange,
            passwordVisible = passwordVisible,
            onPasswordVisibleChange = onPasswordVisibleChange,
            enabled = !loading,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(32.dp))
        SubmitButton(
            text = submitText,
            onSubmit = onSubmit,
            enabled = submitEnabled && !loading,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun NameTextField(
    name: String,
    onNameChange: (String) -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        modifier = modifier,
        enabled = enabled,
        label = { OneLineTitle(text = stringResource(R.string.name)) },
        singleLine = true,
    )
}

@Composable
private fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibleChange: (Boolean) -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
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
        label = { OneLineTitle(text = stringResource(R.string.password)) },
        singleLine = true,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
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

@Composable
private fun SubmitButton(
    text: String,
    onSubmit: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onSubmit,
        modifier = modifier,
        enabled = enabled,
    ) {
        OneLineTitle(text = text)
    }
}

@Composable
private fun ChangeAuthTypeText(
    changeAuthTypeText: String,
    changeAuthTypeClickableText: String,
    onChangeAuthType: () -> Unit,
    loading: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val annotatedText = buildAnnotatedString {
            append(changeAuthTypeText)
            append(' ')

            pushStringAnnotation(tag = "Clickable", annotation = "Clickable")
            val styleColor = if (loading) {
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            } else {
                MaterialTheme.colorScheme.primary
            }
            val style = SpanStyle(
                color = styleColor,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
            )
            withStyle(style) {
                append(changeAuthTypeClickableText)
            }
            pop()
        }
        ClickableText(text = annotatedText) { offset ->
            annotatedText
                .getStringAnnotations(tag = "Clickable", start = offset, end = offset)
                .firstOrNull()
                ?.let { onChangeAuthType() }
        }
    }
}

@Preview
@Composable
private fun AuthContent() {
    AppTheme {
        AuthContent(
            title = stringResource(R.string.welcome_back),
            name = "tuguzT",
            onNameChange = {},
            password = "Hello World",
            onPasswordChange = {},
            passwordVisible = false,
            onPasswordVisibleChange = {},
            submitText = stringResource(R.string.sign_in),
            onSubmit = {},
            submitEnabled = false,
            loading = true,
            changeAuthTypeText = stringResource(R.string.dont_have_an_account),
            changeAuthTypeClickableText = stringResource(R.string.sign_up),
            onChangeAuthType = {},
        )
    }
}
