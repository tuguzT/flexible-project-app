package io.github.tuguzt.flexibleproject.data.repository.settings

import io.github.tuguzt.flexibleproject.domain.model.settings.Language
import io.github.tuguzt.flexibleproject.domain.model.settings.Settings
import io.github.tuguzt.flexibleproject.domain.model.settings.Theme
import io.github.tuguzt.flexibleproject.domain.model.settings.UpdateSettings
import io.github.tuguzt.flexibleproject.domain.model.success
import io.github.tuguzt.flexibleproject.domain.repository.RepositoryResult
import io.github.tuguzt.flexibleproject.domain.repository.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MockSettingsRepository : SettingsRepository {
    override fun read(): Flow<Settings> = settingsStateFlow.asStateFlow()

    override suspend fun update(update: UpdateSettings): RepositoryResult<Settings> {
        val settings = settingsStateFlow.value
        val updated = settings.copy(
            theme = update.theme ?: settings.theme,
            language = update.language ?: settings.language,
        )
        settingsStateFlow.emit(updated)
        return success(updated)
    }

    private val settingsStateFlow = MutableStateFlow(
        value = Settings(theme = Theme.System, language = Language.Russian),
    )
}
