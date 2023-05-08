package io.github.tuguzt.flexibleproject.data.repository.workspace

import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceData
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.domain.repository.workspace.WorkspaceRepository

class MockWorkspaceRepository : WorkspaceRepository {
    private val workspaces: Map<WorkspaceId, WorkspaceData> = mapOf(
        WorkspaceId("1") to WorkspaceData(
            name = "First workspace",
            description = "Empty workspace",
            visibility = Visibility.Public,
            imageUrl = "https://avatars.githubusercontent.com/u/56771526",
        ),
        WorkspaceId("2") to WorkspaceData(
            name = "Second workspace",
            description = "Empty workspace",
            visibility = Visibility.Public,
            imageUrl = null,
        ),
    )

    override suspend fun readAll(): List<Workspace> =
        workspaces.map { (id, data) -> Workspace(id, data) }

    override suspend fun findById(id: WorkspaceId): Workspace? {
        val data = workspaces[id] ?: return null
        return Workspace(id, data)
    }
}
