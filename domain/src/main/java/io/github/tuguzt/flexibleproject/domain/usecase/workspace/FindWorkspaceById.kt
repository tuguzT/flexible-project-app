package io.github.tuguzt.flexibleproject.domain.usecase.workspace

import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.domain.repository.workspace.WorkspaceRepository

class FindWorkspaceById(private val repository: WorkspaceRepository) {
    suspend fun findById(id: WorkspaceId): Workspace? = repository.findById(id)
}
