package io.github.tuguzt.flexibleproject.domain.usecase.workspace

import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.repository.workspace.WorkspaceRepository

class ReadAllWorkspaces(private val repository: WorkspaceRepository) {
    suspend fun readAll(): List<Workspace> = repository.readAll()
}
