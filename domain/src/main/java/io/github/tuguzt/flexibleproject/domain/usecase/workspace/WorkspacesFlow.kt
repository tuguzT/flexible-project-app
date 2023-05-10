package io.github.tuguzt.flexibleproject.domain.usecase.workspace

import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.repository.workspace.WorkspaceRepository
import kotlinx.coroutines.flow.Flow

class WorkspacesFlow(private val repository: WorkspaceRepository) {
    suspend fun workspacesFlow(): Flow<List<Workspace>> = repository.allFlow()
}
