package io.github.tuguzt.flexibleproject.domain.repository.settings

import io.github.tuguzt.flexibleproject.domain.model.settings.Settings
import io.github.tuguzt.flexibleproject.domain.model.settings.UpdateSettings
import io.github.tuguzt.flexibleproject.domain.repository.RepositoryResult
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun read(): Flow<Settings>

    suspend fun update(update: UpdateSettings): RepositoryResult<Settings>
}
