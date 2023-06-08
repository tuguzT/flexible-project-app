package io.github.tuguzt.flexibleproject.view.screens.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.flexibleproject.domain.model.settings.Language
import io.github.tuguzt.flexibleproject.domain.model.settings.Theme
import io.github.tuguzt.flexibleproject.view.theme.AppTheme

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
        // TODO some content
        Box(modifier = Modifier.padding(padding))
    }
}

@Preview
@Composable
private fun SettingsContent() {
    var theme by remember { mutableStateOf(Theme.System) }
    var language by remember { mutableStateOf(Language.System) }

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
