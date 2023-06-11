package io.github.tuguzt.flexibleproject.view.screens.basic.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.BuildConfig
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreenContent(
    onNavigationClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
) {
    Scaffold(
        topBar = {
            AboutTopBar(
                onNavigationClick = onNavigationClick,
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            AppShortDescription()
            Divider(modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp))
            AppVersion(version = BuildConfig.VERSION_NAME)
            Spacer(modifier = Modifier.height(4.dp))
            DevelopedBy()
        }
    }
}

@Composable
private fun AppShortDescription(
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(R.string.app_short_description),
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier,
    )
}

@Composable
private fun AppVersion(
    version: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(R.string.app_version_is, version),
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier,
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun DevelopedBy(
    modifier: Modifier = Modifier,
) {
    val tag = "GitHub Profile"
    val colorScheme = MaterialTheme.colorScheme
    val developedBy = stringResource(R.string.developed_by)
    val uriHandler = LocalUriHandler.current
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = colorScheme.onSurface)) {
            append(developedBy)
        }
        append(' ')
        withAnnotation(tag, annotation = "https://github.com/tuguzT") {
            val style = SpanStyle(
                color = colorScheme.primary,
                fontWeight = FontWeight.Bold,
            )
            withStyle(style) {
                append("@tuguzT")
            }
        }
    }

    ClickableText(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier,
    ) block@{ offset ->
        val annotation = text
            .getStringAnnotations(tag = tag, start = offset, end = offset)
            .firstOrNull() ?: return@block
        val url = annotation.item
        uriHandler.openUri(url)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AboutScreenContent() {
    AppTheme {
        AboutScreenContent(onNavigationClick = {})
    }
}
