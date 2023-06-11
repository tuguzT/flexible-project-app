package io.github.tuguzt.flexibleproject.domain.usecase.project

import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.error
import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.project.Project
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectFilters
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectId
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectIdFilters
import io.github.tuguzt.flexibleproject.domain.model.success
import io.github.tuguzt.flexibleproject.domain.repository.project.ProjectRepository
import kotlinx.coroutines.flow.firstOrNull

class DeleteProject(private val repository: ProjectRepository) {
    suspend fun delete(id: ProjectId): Result<Project, Exception> {
        val filters = ProjectFilters(id = ProjectIdFilters(eq = Equal(id)))
        val project = when (val result = repository.read(filters)) {
            is Result.Error -> return error(Exception.Repository(result.error))
            is Result.Success -> result.data.firstOrNull()?.firstOrNull()
        }
        project ?: return error(Exception.NoProject(id))

        return when (val result = repository.delete(id)) {
            is Result.Error -> error(Exception.Repository(result.error))
            is Result.Success -> success(result.data)
        }
    }

    sealed class Exception(message: String?, cause: Throwable?) : kotlin.Exception(message, cause) {
        data class NoProject(val id: ProjectId) : Exception(
            message = """No project was found by identifier "$id"""",
            cause = null,
        )

        data class Repository(val error: BaseException) : Exception(error.message, error.cause)
    }
}
