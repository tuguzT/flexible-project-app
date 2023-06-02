package io.github.tuguzt.flexibleproject.view.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle

@Composable
fun AuthContent(
    title: String,
    submitText: String,
    onSubmit: () -> Unit,
    submitEnabled: Boolean,
    loading: Boolean,
    changeAuthTypeText: String,
    changeAuthTypeClickableText: String,
    onChangeAuthType: () -> Unit,
    focusManager: FocusManager = LocalFocusManager.current,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    fields: @Composable ColumnScope.() -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) { padding ->
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
                fields = fields,
                submitText = submitText,
                onSubmit = onSubmit,
                submitEnabled = submitEnabled,
                loading = loading,
                modifier = Modifier.weight(1f).padding(vertical = 32.dp),
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
            painter = painterResource(R.mipmap.ic_launcher_foreground),
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
    fields: @Composable ColumnScope.() -> Unit,
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
        fields()
        Spacer(modifier = Modifier.height(32.dp))
        SubmitButton(
            text = submitText,
            onSubmit = onSubmit,
            enabled = submitEnabled && !loading,
            loading = loading,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun SubmitButton(
    text: String,
    onSubmit: () -> Unit,
    enabled: Boolean,
    loading: Boolean,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onSubmit,
        modifier = modifier,
        enabled = enabled,
    ) {
        AnimatedVisibility(visible = loading) {
            Row {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 3.dp,
                    strokeCap = StrokeCap.Round,
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        OneLineTitle(text = text)
    }
}

@OptIn(ExperimentalTextApi::class)
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
        val tag = "clickable"
        val colorScheme = MaterialTheme.colorScheme

        val annotatedText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = colorScheme.onSurface)) {
                append(changeAuthTypeText)
            }
            append(' ')
            withAnnotation(tag = tag, annotation = tag) {
                val styleColor = if (loading) {
                    colorScheme.onSurface.copy(alpha = 0.38f)
                } else {
                    colorScheme.primary
                }
                val style = SpanStyle(
                    color = styleColor,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                )
                withStyle(style) {
                    append(changeAuthTypeClickableText)
                }
            }
        }
        ClickableText(text = annotatedText) { offset ->
            annotatedText
                .getStringAnnotations(tag = tag, start = offset, end = offset)
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
            fields = {},
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
