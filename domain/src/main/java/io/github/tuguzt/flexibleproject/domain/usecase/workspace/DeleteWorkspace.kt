package io.github.tuguzt.flexibleproject.domain.usecase.workspace

import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.error
import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.success
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceFilters
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceIdFilters
import io.github.tuguzt.flexibleproject.domain.repository.workspace.WorkspaceRepository

class DeleteWorkspace(private val repository: WorkspaceRepository) {
    suspend fun delete(id: WorkspaceId): Result<Workspace, Exception> {
        val filters = WorkspaceFilters(id = WorkspaceIdFilters(eq = Equal(id)))
        val workspace = when (val result = repository.read(filters)) {
            is Result.Error -> return error(Exception.Repository(result.error))
            is Result.Success -> result.data.firstOrNull()
        }
        workspace ?: return error(Exception.NoWorkspace(id))

        return when (val result = repository.delete(workspace.id)) {
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
