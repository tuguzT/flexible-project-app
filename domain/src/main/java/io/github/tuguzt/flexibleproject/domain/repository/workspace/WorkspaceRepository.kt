package io.github.tuguzt.flexibleproject.domain.repository.workspace

import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceData
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import kotlinx.coroutines.flow.Flow

interface WorkspaceRepository {
    suspend fun allFlow(): Flow<List<Workspace>>

    suspend fun readAll(): List<Workspace>

    suspend fun findById(id: WorkspaceId): Workspace?

    suspend fun create(data: WorkspaceData): WorkspaceId
}
