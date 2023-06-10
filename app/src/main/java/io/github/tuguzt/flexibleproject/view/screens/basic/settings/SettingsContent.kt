package io.github.tuguzt.flexibleproject.view.screens.basic.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.domain.model.settings.Language
import io.github.tuguzt.flexibleproject.domain.model.settings.Theme
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle
import io.github.tuguzt.flexibleproject.view.utils.toTranslatedString

@Composable
fun SettingsContent(
    theme: Theme,
    onThemeChange: (Theme) -> Unit,
    language: Language,
    onLanguageChange: (Language) -> Unit,
    onNavigationClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            SettingsTopBar(onNavigationClick = onNavigationClick)
        },
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ThemeSettings(
                theme = theme,
                onThemeChange = onThemeChange,
                modifier = Modifier.fillMaxWidth(),
            )
            LanguageSettings(
                language = language,
                onLanguageChange = onLanguageChange,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun SettingsItem(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        modifier = modifier,
        tonalElevation = 1.dp,
    ) {
        Column {
            OneLineTitle(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
            )
            content()
        }
    }
}

@Composable
private fun SelectableItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
) {
    Box(modifier = modifier.clickable(onClick = onClick)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OneLineTitle(text = title)
            if (selected) {
                Icon(
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}

@Composable
private fun ThemeSettings(
    theme: Theme,
    onThemeChange: (Theme) -> Unit,
    modifier: Modifier = Modifier,
) {
    SettingsItem(
        title = stringResource(R.string.theme),
        modifier = modifier,
    ) {
        LazyColumn {
            items(Theme.values(), key = Theme::ordinal) {
                SelectableItem(
                    title = it.toTranslatedString(),
                    selected = it == theme,
                    onClick = { onThemeChange(it) },
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun LanguageSettings(
    language: Language,
    onLanguageChange: (Language) -> Unit,
    modifier: Modifier = Modifier,
) {
    SettingsItem(
        title = stringResource(R.string.language),
        modifier = modifier,
    ) {
        LazyColumn {
            items(Language.values(), key = Language::ordinal) {
                SelectableItem(
                    title = it.toTranslatedString(),
                    selected = it == language,
                    onClick = { onLanguageChange(it) },
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview
@Composable
private fun SettingsContent() {
    var theme by remember { mutableStateOf(Theme.System) }
    var language by remember { mutableStateOf(Language.English) }

    val darkTheme = when (theme) {
        Theme.System -> isSystemInDarkTheme()
        Theme.Light -> false
        Theme.Dark -> true
    }
    AppTheme(darkTheme) {
        SettingsContent(
            theme = theme,
            onThemeChange = { theme = it },
            language = language,
            onLanguageChange = { language = it },
            onNavigationClick = {},
        )
    }
}
