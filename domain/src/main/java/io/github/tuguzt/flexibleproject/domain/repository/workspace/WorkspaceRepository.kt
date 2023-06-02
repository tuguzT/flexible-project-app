package io.github.tuguzt.flexibleproject.domain.repository.workspace

import io.github.tuguzt.flexibleproject.domain.model.workspace.UpdateWorkspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceData
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceFilters
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.domain.repository.RepositoryResult
import kotlinx.coroutines.flow.Flow

interface WorkspaceRepository {
    suspend fun create(data: WorkspaceData): RepositoryResult<Workspace>

    suspend fun read(filters: WorkspaceFilters): RepositoryResult<Flow<List<Workspace>>>

    suspend fun update(id: WorkspaceId, update: UpdateWorkspace): RepositoryResult<Workspace>

    suspend fun delete(id: WorkspaceId): RepositoryResult<Workspace>
}
