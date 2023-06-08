package io.github.tuguzt.flexibleproject.domain.usecase.settings

import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.settings.Settings
import io.github.tuguzt.flexibleproject.domain.model.settings.UpdateSettings
import io.github.tuguzt.flexibleproject.domain.model.success
import io.github.tuguzt.flexibleproject.domain.repository.settings.SettingsRepository

class UpdateSettings(private val repository: SettingsRepository) {
    suspend fun update(update: UpdateSettings): Result<Settings, Exception> {
        return when (val result = repository.update(update)) {
            is Result.Error -> error(Exception.Repository(result.error))
            is Result.Success -> success(result.data)
        }
    }

    sealed class Exception(message: String?, cause: Throwable?) : kotlin.Exception(message, cause) {
        data class Repository(val error: BaseException) : Exception(error.message, error.cause)
    }
}
