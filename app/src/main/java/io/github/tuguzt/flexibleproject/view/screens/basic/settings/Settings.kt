package io.github.tuguzt.flexibleproject.view.screens.basic.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.tuguzt.flexibleproject.view.screens.basic.BasicNavGraph
import io.github.tuguzt.flexibleproject.viewmodel.basic.settings.SettingsViewModel
import io.github.tuguzt.flexibleproject.viewmodel.basic.settings.store.SettingsStore.Intent

@OptIn(ExperimentalMaterial3Api::class)
@BasicNavGraph
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
