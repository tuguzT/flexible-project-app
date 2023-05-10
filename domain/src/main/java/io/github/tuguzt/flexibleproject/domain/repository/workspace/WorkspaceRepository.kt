package io.github.tuguzt.flexibleproject.domain.repository.workspace

import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceData
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId

interface WorkspaceRepository {
    suspend fun readAll(): List<Workspace>

    suspend fun findById(id: WorkspaceId): Workspace?

    suspend fun create(data: WorkspaceData): WorkspaceId
}
