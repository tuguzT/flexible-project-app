package io.github.tuguzt.flexibleproject.view.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.tuguzt.flexibleproject.viewmodel.settings.SettingsViewModel
import io.github.tuguzt.flexibleproject.viewmodel.settings.store.SettingsStore.Intent

@RootNavGraph
@Destination
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
    viewModel: SettingsViewModel,
) {
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    SettingsContent(
        theme = state.theme,
        onThemeChange = { theme ->
            val intent = Intent.ChangeTheme(theme)
            viewModel.accept(intent)
        },
        language = state.language,
        onLanguageChange = { language ->
            val intent = Intent.ChangeLanguage(language)
            viewModel.accept(intent)
        },
        onNavigationClick = navigator::navigateUp,
    )
}
