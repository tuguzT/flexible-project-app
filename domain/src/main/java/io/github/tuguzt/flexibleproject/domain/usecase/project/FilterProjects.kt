package io.github.tuguzt.flexibleproject.domain.usecase.project

import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.error
import io.github.tuguzt.flexibleproject.domain.model.project.Project
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectFilters
import io.github.tuguzt.flexibleproject.domain.model.success
import io.github.tuguzt.flexibleproject.domain.repository.project.ProjectRepository
import kotlinx.coroutines.flow.Flow

class FilterProjects(private val repository: ProjectRepository) {
    suspend fun projects(filters: ProjectFilters): Result<Flow<List<Project>>, Exception> {
        return when (val result = repository.read(filters)) {
            is Result.Error -> error(Exception.Repository(result.error))
            is Result.Success -> success(result.data)
        }
    }

    sealed class Exception(message: String?, cause: Throwable?) : kotlin.Exception(message, cause) {
        data class Repository(val error: BaseException) : Exception(error.message, error.cause)
    }
}
