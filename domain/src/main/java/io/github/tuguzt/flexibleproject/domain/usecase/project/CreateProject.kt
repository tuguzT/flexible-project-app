package io.github.tuguzt.flexibleproject.domain.usecase.project

import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.error
import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.project.Project
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectData
import io.github.tuguzt.flexibleproject.domain.model.success
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceFilters
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceIdFilters
import io.github.tuguzt.flexibleproject.domain.repository.project.ProjectRepository
import io.github.tuguzt.flexibleproject.domain.repository.workspace.WorkspaceRepository
import kotlinx.coroutines.flow.firstOrNull

class CreateProject(
    private val projectRepository: ProjectRepository,
    private val workspaceRepository: WorkspaceRepository,
) {
    suspend fun create(data: ProjectData): Result<Project, Exception> {
        val workspaceId = data.workspace
        val filters = WorkspaceFilters(id = WorkspaceIdFilters(eq = Equal(workspaceId)))
        val workspace = when (val result = workspaceRepository.read(filters)) {
            is Result.Error -> return error(Exception.Repository(result.error))
            is Result.Success -> result.data.firstOrNull()?.firstOrNull()
        }
        workspace ?: return error(Exception.NoWorkspace(workspaceId))

        return when (val result = projectRepository.create(data)) {
            is Result.Error -> error(Exception.Repository(result.error))
            is Result.Success -> success(result.data)
        }
    }

    sealed class Exception(message: String?, cause: Throwable?) : kotlin.Exception(message, cause) {
        data class NoWorkspace(val id: WorkspaceId) : Exception(
            message = """No workspace was found by identifier "$id"""",
            cause = null,
        )

        data class Repository(val error: BaseException) : Exception(error.message, error.cause)
    }
}
