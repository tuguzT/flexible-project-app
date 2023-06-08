package io.github.tuguzt.flexibleproject.domain.usecase.settings

import io.github.tuguzt.flexibleproject.domain.model.settings.Settings
import io.github.tuguzt.flexibleproject.domain.repository.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetSettings(private val repository: SettingsRepository) {
    suspend fun settings(): Flow<Settings> = repository.read()
}
