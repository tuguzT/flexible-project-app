package io.github.tuguzt.flexibleproject.domain.usecase.workspace

import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceData
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.domain.repository.workspace.WorkspaceRepository

class CreateWorkspace(private val repository: WorkspaceRepository) {
    suspend fun create(data: WorkspaceData): WorkspaceId = repository.create(data)
}
